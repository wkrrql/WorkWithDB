package by.bsu.dao.impl;

import by.bsu.entity.Film;
import by.bsu.exception.DaoException;
import by.bsu.specification.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDaoImpl {
    private static final Logger logger = LoggerFactory.getLogger(FilmDaoImpl.class);
    private final Connection connection;
    private static final String SELECT_BASE = "SELECT ID, Name, ReleaseDate, Country FROM home_cinema.films ";

    public FilmDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Film> query(Specification specification) throws DaoException {
        List<Film> films = new ArrayList<>();
        String sql = SELECT_BASE + (specification != null ? specification.toSqlClause() : "");

        logger.debug("Выполнение SQL запроса чтения: {}", sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (specification != null) {
                specification.setParameters(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Date sqlDate = resultSet.getDate("ReleaseDate");
                    Film film = new Film.Builder()
                            .id(resultSet.getInt("ID"))
                            .name(resultSet.getString("Name"))
                            .releaseDate(sqlDate != null ? sqlDate.toLocalDate() : null)
                            .country(resultSet.getString("Country"))
                            .build();
                    films.add(film);
                }
            }
        } catch (SQLException e) {
            logger.error("Критическая ошибка SQL при чтении таблицы 'films': {}", e.getMessage(), e);
            throw new DaoException("Ошибка уровня DAO при чтении данных", e);
        }
        return films;
    }
}