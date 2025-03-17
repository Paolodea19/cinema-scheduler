package it.lascaux.cinemascheduler.services;

import it.lascaux.cinemascheduler.models.Film;
import it.lascaux.cinemascheduler.repositories.FilmRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FilmService {


    private FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmById(long id) {
        return filmRepository.findById(id).orElse(null);
    }

    public Film save(Film film) {
        return filmRepository.save(film);
    }

    public void importFilmsFromExcel(MultipartFile file) throws IOException {
        List<Film> films = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Prende il primo foglio
            Iterator<Row> rowIterator = sheet.iterator();

            // Salto intestazione(prima riga)
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Film film = new Film();
                film.setYear(getCellValueAsString(row.getCell(0)));  // Prima colonna
                film.setTitle(getCellValueAsString(row.getCell(1))); // Seconda colonna
                film.setDirector(getCellValueAsString(row.getCell(2))); // Terza colonna
                film.setDurata((int) row.getCell(3).getNumericCellValue()); // Quarta colonna
                film.setImageUrl(getCellValueAsString(row.getCell(4))); // Quinta colonna

                films.add(film);
            }
        }

        filmRepository.saveAll(films);
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

}
