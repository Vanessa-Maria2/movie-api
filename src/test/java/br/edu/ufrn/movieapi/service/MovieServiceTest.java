package br.edu.ufrn.movieapi.service;

import br.edu.ufrn.movieapi.dto.MovieResponseDto;
import br.edu.ufrn.movieapi.model.Genre;
import br.edu.ufrn.movieapi.model.Movie;
import br.edu.ufrn.movieapi.repository.GenreRepository;
import br.edu.ufrn.movieapi.repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    Movie movie, movie2;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();

        movie = new Movie();
        movie.setId(1L);
        movie.setOriginal_title("Inception");
        movie.setTitle("A Origem");
        movie.setOriginal_language("en");
        movie.setOverview("Um ladrão que invade os sonhos das pessoas para roubar segredos corporativos.");
        movie.setPopularity(98.5f);
        movie.setRelease_date(Date.from(LocalDate.of(2010, 7, 16).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        movie.setPoster_path("/inception_poster.jpg");
        movie.setBackdrop_path("/inception_backdrop.jpg");
        movie.setVideo(false);
        movie.setVote_average(8);
        movie.setVote_count(12000);
        movie.setAdult(false);

        movie2 = new Movie();
        movie2.setId(2L);
        movie2.setOriginal_title("Interstellar");
        movie2.setTitle("Interestelar");
        movie2.setOriginal_language("en");
        movie2.setOverview("Uma equipe viaja através de um buraco de minhoca...");
        movie2.setPopularity(97.8f);
        movie2.setRelease_date(Date.from(LocalDate.of(2012, 8, 28).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        movie2.setPoster_path("/poster_interstellar.jpg");
        movie2.setBackdrop_path("/backdrop_interstellar.jpg");
        movie2.setVideo(false);
        movie2.setVote_average(9);
        movie2.setVote_count(25000);
        movie2.setAdult(false);

        movieRepository.saveAll(List.of(movie, movie2));
    }

    @Named("Register movie successfully")
    @Test
    void saveMovie() throws ParseException, JsonProcessingException {
        MovieResponseDto movieSaved = movieService.saveMovie("wall");
        assertNotNull(movieSaved);
    }

    @Named("When trying to register a movie that does not exist, the entity not found exception is thrown.")
    @Test
    void saveMovieError() throws ParseException, JsonProcessingException {
        assertThrows(EntityNotFoundException.class, () -> {
            movieService.saveMovie("isie43jdidi3h");
        });
    }

    @Named("Search for movie by title in tmdb public API")
    @Test
    void searchMovieByTitle() throws ParseException, JsonProcessingException {
        Movie movie = movieService.searchMovieByTitle("Interstellar");
        assertEquals(movie.getTitle(), "Interstellar");
    }

    @Named("Checks if the movie entity fetched by the given ID exists")
    @Test
    void existsMovieById() {
        boolean exists = movieService.existsMovieById(movie.getId_movie());
        assertTrue(exists);
    }

    @Named("Search all movies")
    @Test
    void getAllMovies() {
        List<MovieResponseDto> movieResponseDtos = movieService.getAllMovies();
        assertEquals(2, movieResponseDtos.size());
    }

    @Named("Search movie by given ID")
    @Test
    void getMovieById() {
        Optional<MovieResponseDto> movieResponseDto = movieService.getMovieById(movie.getId_movie());
        assertNotNull(movieResponseDto);
    }

    @Named("Movie entity deleted by id")
    @Test
    void deleteMovieById() {
        boolean result = movieService.deleteMovieById(movie.getId_movie());
        assertTrue(result);
    }
}