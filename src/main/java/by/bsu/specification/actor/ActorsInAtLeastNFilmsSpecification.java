package by.bsu.specification.actor;

import by.bsu.specification.Specification;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsInAtLeastNFilmsSpecification implements Specification {
    private final int minFilms;

    public ActorsInAtLeastNFilmsSpecification(int minFilms) {
        this.minFilms = minFilms;
    }

    @Override
    public String toSqlClause() {
        return "JOIN film_actors fa ON actors.ID = fa.ActorID " +
                "GROUP BY actors.ID, actors.ActorsFullName, actors.ActorsBirthday " +
                "HAVING COUNT(fa.FilmID) >= ?";
    }

    @Override
    public void setParameters(PreparedStatement statement) throws SQLException {
        statement.setInt(1, minFilms);
    }
}