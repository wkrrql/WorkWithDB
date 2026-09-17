package by.bsu.dao.impl;

import by.bsu.entity.Film;
import by.bsu.exception.DaoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FilmUpdateDaoImpl {
    private final Connection connection;
    private static final String INSERT_FILM = "INSERT INTO home_cinema.films (Name, ReleaseDate, Country) VALUES (?, ?, ?)";
    private static final String DELETE_OLD_FILMS = "DELETE FROM home_cinema.films WHERE ReleaseDate < DATE_SUB(CURDATE(), INTERVAL ? YEAR)";

    public FilmUpdateDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void save(Film film) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_FILM)) {
            statement.setString(1, film.getName());
            statement.setDate(2, film.getReleaseDate() != null ? Date.valueOf(film.getReleaseDate()) : null);
            statement.setString(3, film.getCountry());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Ошибка модификации данных: не удалось сохранить фильм " + film.getName(), e);
        }
    }

    public int deleteOlderThanYears(int years) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_OLD_FILMS)) {
            statement.setInt(1, years);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Ошибка модификации данных: не удалось удалить старые фильмы", e);
        }
    }
}