package br.edu.ufrn.movieapi.mapper;

import br.edu.ufrn.movieapi.dto.MovieResponseDto;
import br.edu.ufrn.movieapi.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toMovie(MovieResponseDto movieResponseDto);

    MovieResponseDto toMovieResponseDto(Movie movie);
}
