package br.edu.ufrn.movieapi.controller;

import br.edu.ufrn.movieapi.dto.MovieResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.ufrn.movieapi.service.MovieService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movie-api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        List<MovieResponseDto> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable Long id) {
        Optional<MovieResponseDto> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieResponseDto> saveMovie(@RequestParam String title) throws Exception {
        MovieResponseDto movie = movieService.saveMovie(title);
        return ResponseEntity.ok(movie);
    }
}
