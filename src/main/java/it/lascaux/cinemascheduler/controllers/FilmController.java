package it.lascaux.cinemascheduler.controllers;

import it.lascaux.cinemascheduler.models.Film;
import it.lascaux.cinemascheduler.services.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/film")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<Film>> getFilms() {
        List<Film> films = filmService.getAllFilms();
        if (films.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(films);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PostMapping
    public ResponseEntity<?> saveFilm(@RequestBody Film film) {
        try {
            Film savedFilm = filmService.save(film);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFilm);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dati del film non validi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il salvataggio del film.");
        }
    }

    @PostMapping("/uploadFilms")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            filmService.importFilmsFromExcel(file);
            return ResponseEntity.ok("File Excel importato con successo!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Errore durante l'importazione del file Excel.");
        }
    }

}
