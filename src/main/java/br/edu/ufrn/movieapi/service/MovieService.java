package br.edu.ufrn.movieapi.service;

import br.edu.ufrn.movieapi.dto.MovieResponseApiDto;
import br.edu.ufrn.movieapi.dto.MovieResponseDto;
import br.edu.ufrn.movieapi.mapper.MovieMapper;
import br.edu.ufrn.movieapi.model.Genre;
import br.edu.ufrn.movieapi.model.Movie;
import br.edu.ufrn.movieapi.repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    final RestTemplate restTemplate;

    private final String apiKey;

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    private final GenreService genreService;

    public MovieService(RestTemplate restTemplate,  @Value("${api.key}") String apiKey, MovieRepository movieRepository, MovieMapper movieMapper, GenreService genreService) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.genreService = genreService;
    }

    public MovieResponseDto saveMovie(String title) throws ParseException, JsonProcessingException {
        Movie movie = searchMovieByTitle(title);
        if (existsMovieById(movie.getId())) {
            throw new EntityExistsException("Movie already exists");
        }
        movieRepository.save(movie);
        return movieMapper.toMovieResponseDto(movie);
    }

    public Movie searchMovieByTitle(String title) throws JsonProcessingException, ParseException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        headers.set("accept", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/search/movie?query=" + title + "&include_adult=false&language=en-US&page=1",
                HttpMethod.GET,
                request,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode resultsNode = rootNode.path("results");
        JsonNode firstMovie = resultsNode.get(0);

        MovieResponseApiDto movieResponseApiDto = objectMapper.treeToValue(firstMovie, MovieResponseApiDto.class);

        List<Genre> genreList = genreService.getAllGenres(movieResponseApiDto.getGenre_ids());

        Movie movie = new Movie();
        BeanUtils.copyProperties(movieResponseApiDto, movie);
        movie.setGenre_ids(genreList);

        return movie;
    }

    public boolean existsMovieById(Long id) {
        return movieRepository.existsById_movie(id);
    }

    public List<MovieResponseDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movieMapper::toMovieResponseDto).toList();
    }

    public Optional<MovieResponseDto> getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        return Optional.ofNullable(movieMapper.toMovieResponseDto(movie));
    }

    public boolean deleteMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot delete movie. Movie with the given ID does not exist!"));
        movieRepository.delete(movie);
        return true;
    }

}
