package it.lascaux.cinemascheduler.repositories;

import it.lascaux.cinemascheduler.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
