package by.bsu.run;

import by.bsu.dao.impl.ActorDaoImpl;
import by.bsu.dao.impl.FilmDaoImpl;
import by.bsu.dao.impl.FilmUpdateDaoImpl;
import by.bsu.entity.Director;
import by.bsu.entity.Film;
import by.bsu.entity.Actor;
import by.bsu.service.CinemaService;
import by.bsu.validator.FilmValidator;
import by.bsu.connection.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try (Connection connection = ConnectionFactory.getConnection()) {

            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            ActorDaoImpl actorDao = new ActorDaoImpl(connection);
            FilmUpdateDaoImpl filmUpdateDao = new FilmUpdateDaoImpl(connection);
            FilmValidator filmValidator = new FilmValidator();

            CinemaService cinemaService = new CinemaService(filmDao, actorDao, filmUpdateDao, filmValidator);

//            //добавление режиссёра
//            Director sampleDirector = new Director.Builder()
//                    .id(1)
//                    .fullName("Кристофер Нолан")
//                    .birthday(LocalDate.of(1970, 7, 30))
//                    .build();
//            logger.info("Метаданные структуры. Загружен режиссер: {}", sampleDirector.getFullName());

//            //Вывод фильмов за текущий и прошлай года
//            logger.info(" Задание 1 ");
//            List<Film> recentFilms = cinemaService.getRecentFilms();
//            if (recentFilms.isEmpty()) {
//                logger.info("Задание 1: Фильмы за текущий и прошлый годы не найдены.");
//            } else {
//                recentFilms.forEach(film -> logger.info("Найдена кинолента: {}, Классика? -> {}",
//                        film.getName(), cinemaService.isFilmClassic(film)));
//            }

//            //Вывод актеров фильма 'Начало'
//            logger.info(" Задание 2 ");
//            List<Actor> filmActors = cinemaService.getActorsByFilm("Начало");
//            if (filmActors.isEmpty()) {
//                logger.info("Задание 2: Актеры для фильма 'Начало' не найдены в БД.");
//            } else {
//                filmActors.forEach(actor -> logger.info("Актер в фильме 'Начало': {}", actor.getFullName()));
//            }
//
//            //Вывод популярных актеров
//            logger.info(" Задание 3 ");
//            List<Actor> popularActors = cinemaService.getActorsWithMinimumFilms(2);
//            if (popularActors.isEmpty()) {
//                logger.info("Задание 3: Не найдено актеров, снявшихся как минимум в 2 фильмах.");
//            } else {
//                popularActors.forEach(actor -> logger.info("Актер снялся в >= 2 фильмах: {}", actor.getFullName()));
//            }
//
//            //Вывод актеров-режиссеров
//            logger.info(" Задание 4 ");
//            List<Actor> actorDirectors = cinemaService.getActorsWhoAreDirectors();
//            if (actorDirectors.isEmpty()) {
//                logger.info("Задание 4: Актеры, которые также являются режиссерами, не найдены.");
//            } else {
//                actorDirectors.forEach(actor -> logger.info("Актер-режиссер: {}", actor.getFullName()));
//            }
//
            //Удаление фильмов старше N лет
            logger.info(" Задание 5 ");
            cinemaService.deleteOldFilms(10); //N

        } catch (Exception e) {
            logger.error("Критический сбой на верхнем уровне приложения: {}", e.getMessage(), e);
        }

        logger.info("Работа приложения Видеотеки успешно завершена.");
    }
}