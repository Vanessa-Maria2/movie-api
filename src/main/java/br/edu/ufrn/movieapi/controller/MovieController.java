package br.edu.ufrn.movieapi.controller;

import br.edu.ufrn.movieapi.dto.MovieResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.ufrn.movieapi.service.MovieService;

@RestController
@RequestMapping("movie-api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponseDto> saveMovie(@RequestParam String title) throws Exception {
        MovieResponseDto movie = movieService.saveMovie(title);
        return ResponseEntity.ok(movie);
    }
}
