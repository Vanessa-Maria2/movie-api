package br.edu.ufrn.movieapi.repository;

import br.edu.ufrn.movieapi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Movie m WHERE m.id = :id")
    boolean existsById_movie(@Param("id") Long id);
}
