package by.bsu.service;

import by.bsu.dao.impl.ActorDaoImpl;
import by.bsu.dao.impl.FilmDaoImpl;
import by.bsu.dao.impl.FilmUpdateDaoImpl;
import by.bsu.entity.Film;
import by.bsu.exception.DaoException;
import by.bsu.exception.ServiceException;
import by.bsu.specification.Specification;
import by.bsu.validator.FilmValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CinemaServiceTest {

    @Mock
    private FilmDaoImpl filmDao;

    @Mock
    private ActorDaoImpl actorDao;

    @Mock
    private FilmUpdateDaoImpl filmUpdateDao;

    @Mock
    private FilmValidator filmValidator;

    @InjectMocks
    private CinemaService cinemaService;

    @Test
    @DisplayName("getRecentFilms должен успешно возвращать список фильмов из DAO")
    void shouldReturnRecentFilmsSuccessfully() throws DaoException, ServiceException {
        Film expectedFilm = new Film.Builder().id(1).name("Inception").releaseDate(LocalDate.now()).build();
        List<Film> expectedList = List.of(expectedFilm);

        Mockito.when(filmDao.query(any(Specification.class))).thenReturn(expectedList);

        List<Film> actualList = cinemaService.getRecentFilms();

        assertAll("Проверка корректности возвращенных данных",
                () -> assertEquals(1, actualList.size()),
                () -> assertEquals("Inception", actualList.get(0).getName())
        );
    }

    @Test
    @DisplayName("addValidFilm должен выбрасывать ServiceException, если валидатор вернул false")
    void shouldThrowExceptionWhenFilmIsInvalid() {
        Film invalidFilm = new Film.Builder().name("").build();
        Mockito.when(filmValidator.isValid(invalidFilm)).thenReturn(false);

        assertThrows(ServiceException.class, () -> cinemaService.addValidFilm(invalidFilm));
    }
}