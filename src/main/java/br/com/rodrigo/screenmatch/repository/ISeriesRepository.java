package br.com.rodrigo.screenmatch.repository;

import br.com.rodrigo.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeriesRepository extends JpaRepository<Series, Long> {
}
