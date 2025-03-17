package it.lascaux.cinemascheduler.repositories;

import it.lascaux.cinemascheduler.models.Programmazione;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgrammazioneRepository extends JpaRepository<Programmazione, Long> {
    List<Programmazione> findByDataOraInizioBetween(LocalDate start, LocalDate end);
    // Trova programmazione per sala
    Optional<Programmazione> findBySala(String sala);
}
