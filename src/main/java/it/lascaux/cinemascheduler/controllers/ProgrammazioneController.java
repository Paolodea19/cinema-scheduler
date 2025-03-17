package it.lascaux.cinemascheduler.controllers;

import it.lascaux.cinemascheduler.models.Programmazione;
import it.lascaux.cinemascheduler.services.ProgrammazioneService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/programmazione")
public class ProgrammazioneController {

    private final ProgrammazioneService programmazioneService;

    public ProgrammazioneController(ProgrammazioneService programmazioneService) {
        this.programmazioneService = programmazioneService;
    }

    @GetMapping
    public List<Programmazione> getProgrammazione(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return programmazioneService.getProgrammazioneByDate(start, end);
    }

    @GetMapping("/{sala}")
    public Programmazione getProgrammazioneBySala(@PathVariable String sala) {
        return programmazioneService.getProgrammazioneBySala(sala);
    }

    @PostMapping
    public Programmazione createProgrammazione(@RequestBody Programmazione programmazione) {
        return programmazioneService.saveProgrammazione(programmazione);
    }
}
