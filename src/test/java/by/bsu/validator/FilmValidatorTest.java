package by.bsu.validator;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.xml.validation.Validator;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmValidatorTest {

    @Test
    @DisplayName("Проверка на валидность для фильма. Должна проходить")
    void CorrectFilm(){
        FilmValidator validator = new FilmValidator();
        boolean result = validator.isValid("Начало", LocalDate.of(2002, 2, 8));
        assertTrue(result, "Фильм с корректными данными валиден");
    }

    @ParameterizedTest
    @DisplayName("Невалидные данные не проходят")
    @CsvSource({
            ", 2020-01-02",    //просто проверка названия(null) фильма
            "' ', 2020-01-02",   //пустое имя
            "'    ', 2020-01-02"   //имя - множество пробелов
    })
    void UncorrectFilm(String name, String dateStr){
        FilmValidator validator = new FilmValidator();
        LocalDate date = LocalDate.parse(dateStr);

        boolean result = validator.isValid(name, date);

        assertFalse(result, "Фильс с некорректными данными не проходит");

    }

}
