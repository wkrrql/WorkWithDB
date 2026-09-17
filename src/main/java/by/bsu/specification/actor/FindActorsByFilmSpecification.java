package by.bsu.specification.actor;

import by.bsu.specification.Specification;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindActorsByFilmSpecification implements Specification {
    private final String filmName;

    public FindActorsByFilmSpecification(String filmName) {
        this.filmName = filmName;
    }

    @Override
    public String toSqlClause() {
        return "JOIN home_cinema.film_actors fa ON actors.ID = fa.ActorID " +
                "JOIN home_cinema.films f ON fa.FilmID = f.ID " +
                "WHERE f.Name = ?";
    }

    @Override
    public void setParameters(PreparedStatement statement) throws SQLException {
        statement.setString(1, filmName);
    }
}