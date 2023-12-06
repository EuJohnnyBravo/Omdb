package br.com.rodrigo.screenmatch.repository;

import br.com.rodrigo.screenmatch.model.Category;
import br.com.rodrigo.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String seriesName);

    List<Series> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actorName, double rating);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Category category);
}
