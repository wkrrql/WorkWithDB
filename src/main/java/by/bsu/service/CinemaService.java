package by.bsu.service;

import by.bsu.dao.impl.ActorDaoImpl;
import by.bsu.dao.impl.FilmDaoImpl;
import by.bsu.dao.impl.FilmUpdateDaoImpl;
import by.bsu.entity.Actor;
import by.bsu.entity.Film;
import by.bsu.exception.DaoException;
import by.bsu.exception.ServiceException;
import by.bsu.specification.actor.ActorsInAtLeastNFilmsSpecification;
import by.bsu.specification.actor.FindActorsByFilmSpecification;
import by.bsu.specification.actor.FindActorsWhoAreDirectorsSpecification;
import by.bsu.specification.film.FindFilmsByYearSpecification;
import by.bsu.validator.FilmValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class CinemaService {
    private static final Logger logger = LoggerFactory.getLogger(CinemaService.class);

    private final FilmDaoImpl filmDao;
    private final ActorDaoImpl actorDao;
    private final FilmUpdateDaoImpl filmUpdateDao;
    private final FilmValidator filmValidator; // Исправление Нарушения 5.2

    public CinemaService(FilmDaoImpl filmDao, ActorDaoImpl actorDao, FilmUpdateDaoImpl filmUpdateDao, FilmValidator filmValidator) {
        this.filmDao = filmDao;
        this.actorDao = actorDao;
        this.filmUpdateDao = filmUpdateDao;
        this.filmValidator = filmValidator;
    }

    // Задание 1
    public List<Film> getRecentFilms() throws ServiceException {
        logger.info("Запрос на получение списка недавних фильмов");
        try {
            return filmDao.query(new FindFilmsByYearSpecification());
        } catch (DaoException e) {
            throw new ServiceException("Ошибка сервиса при получении недавних фильмов", e);
        }
    }

    public boolean isFilmClassic(Film film) {
        if (film == null || film.getReleaseDate() == null) {
            logger.warn("Передан пустой фильм для проверки статуса Классики");
            return false;
        }
        boolean isClassic = film.getReleaseDate().isBefore(LocalDate.now().minusYears(20));
        logger.debug("Фильм '{}' классический? -> {}", film.getName(), isClassic);
        return isClassic;
    }

    public void addValidFilm(Film film) throws ServiceException {
        if (!filmValidator.isValid(film)) {
            logger.warn("Бизнес-валидация отклонила добавление фильма: {}", film);
            throw new ServiceException("Данные фильма не прошли валидацию структуры!");
        }
        try {
            filmUpdateDao.save(film);
            logger.info("Фильм '{}' успешно сохранен через сервис.", film.getName());
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при сохранении валидного фильма", e);
        }
    }

    // Задание 2
    public List<Actor> getActorsByFilm(String filmName) throws ServiceException {
        logger.info("Запрос актеров для фильма: {}", filmName);
        try {
            return actorDao.query(new FindActorsByFilmSpecification(filmName));
        } catch (DaoException e) {
            throw new ServiceException("Ошибка сервиса при поиске актеров по фильму", e);
        }
    }

    // Задание 3
    public List<Actor> getActorsWithMinimumFilms(int nFilms) throws ServiceException {
        logger.info("Запрос актеров, снявшихся минимум в {} фильмах", nFilms);
        try {
            return actorDao.query(new ActorsInAtLeastNFilmsSpecification(nFilms));
        } catch (DaoException e) {
            throw new ServiceException("Ошибка сервиса при поиске популярных актеров", e);
        }
    }

    // Задание 4
    public List<Actor> getActorsWhoAreDirectors() throws ServiceException {
        logger.info("Запрос актеров, являющихся режиссерами");
        try {
            return actorDao.query(new FindActorsWhoAreDirectorsSpecification());
        } catch (DaoException e) {
            throw new ServiceException("Ошибка сервиса при поиске актеров-режиссеров", e);
        }
    }

    // Задание 5
    public void deleteOldFilms(int years) throws ServiceException {
        logger.info("Запрос на удаление фильмов старше {} лет", years);
        try {
            int deletedCount = filmUpdateDao.deleteOlderThanYears(years);
            logger.info("Успешно завершено удаление. Количество удаленных кинолент: {}", deletedCount);
        } catch (DaoException e) {
            throw new ServiceException("Ошибка сервиса при каскадном удалении фильмов", e);
        }
    }
}