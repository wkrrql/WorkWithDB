package by.bsu.dao.impl;

import by.bsu.entity.Actor;
import by.bsu.exception.DaoException;
import by.bsu.specification.Specification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDaoImpl {
    private final Connection connection;
    private static final String SELECT_BASE = "SELECT actors.ID, actors.ActorsFullName, actors.ActorsBirthday FROM home_cinema.actors ";

    public ActorDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Actor> query(Specification specification) throws DaoException {
        List<Actor> actors = new ArrayList<>();
        String sql = SELECT_BASE + (specification != null ? specification.toSqlClause() : "");

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (specification != null) {
                specification.setParameters(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Date sqlDate = resultSet.getDate("ActorsBirthday");
                    Actor actor = new Actor.Builder()
                            .id(resultSet.getInt("ID"))
                            .fullName(resultSet.getString("ActorsFullName"))
                            .birthday(sqlDate != null ? sqlDate.toLocalDate() : null)
                            .build();
                    actors.add(actor);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка при поиске актеров в БД home_cinema", e);
        }
        return actors;
    }
}