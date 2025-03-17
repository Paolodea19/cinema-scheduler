package it.lascaux.cinemascheduler.services;

import it.lascaux.cinemascheduler.models.Programmazione;
import it.lascaux.cinemascheduler.repositories.ProgrammazioneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProgrammazioneService {

    private final ProgrammazioneRepository programmazioneRepository;


    public ProgrammazioneService(ProgrammazioneRepository programmazioneRepository) {
        this.programmazioneRepository = programmazioneRepository;
    }

    public List<Programmazione> getProgrammazioneByDate(LocalDate start, LocalDate end) {
        return programmazioneRepository.findByDataOraInizioBetween(start, end);
    }

    // Recupera la programmazione per sala
    public Programmazione getProgrammazioneBySala(String sala) {
        // Metodo personalizzato nel repository (findBySala)
        Optional<Programmazione> programmazione = programmazioneRepository.findBySala(sala);
        return programmazione.orElseThrow(() -> new NoSuchElementException("Programmazione non trovata per la sala: " + sala));
    }


    public Programmazione saveProgrammazione(Programmazione programmazione) {
        return programmazioneRepository.save(programmazione);
    }
}
