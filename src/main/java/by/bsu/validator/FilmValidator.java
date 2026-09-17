package by.bsu.validator;

import by.bsu.entity.Film;
import java.time.LocalDate;

public class FilmValidator {

    public boolean isValid(Film film) {
        if (film == null) {
            return false;
        }

        boolean isNameValid = film.getName() != null && !film.getName().trim().isEmpty();
        boolean isCountryValid = film.getCountry() != null && !film.getCountry().trim().isEmpty();
        boolean isReleaseDateValid = film.getReleaseDate() != null
                && film.getReleaseDate().isBefore(LocalDate.now().plusYears(5));

        return isNameValid && isCountryValid && isReleaseDateValid;
    }

    public boolean isValid(String filmName) {
        return filmName != null && !filmName.trim().isEmpty();
    }

    public boolean isValid(String name, LocalDate date) {
        boolean isNameValid = name != null && !name.trim().isEmpty();
        boolean isReleaseDateValid = date != null && date.isBefore(LocalDate.now().plusYears(5));
        return isNameValid && isReleaseDateValid;
    }
}