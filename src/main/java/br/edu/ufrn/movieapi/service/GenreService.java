package br.edu.ufrn.movieapi.service;

import br.edu.ufrn.movieapi.model.Genre;
import br.edu.ufrn.movieapi.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres(List<Integer> ids) {
        return (List<Genre>) genreRepository.findAllById(ids);
    }
}
