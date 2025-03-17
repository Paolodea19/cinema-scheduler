package it.lascaux.cinemascheduler.models;

import jakarta.persistence.*;


import java.time.LocalDate;


@Entity
public class Programmazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    private LocalDate dataOraInizio;
    private LocalDate dataOraFine;
    private String sala;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public LocalDate getDataOraInizio() {
        return dataOraInizio;
    }

    public void setDataOraInizio(LocalDate dataOraInizio) {
        this.dataOraInizio = dataOraInizio;
    }

    public LocalDate getDataOraFine() {
        return dataOraFine;
    }

    public void setDataOraFine(LocalDate dataOraFine) {
        this.dataOraFine = dataOraFine;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }
}
