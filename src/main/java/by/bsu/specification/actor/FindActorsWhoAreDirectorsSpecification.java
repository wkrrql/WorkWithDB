package by.bsu.specification.actor;

import by.bsu.specification.Specification;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindActorsWhoAreDirectorsSpecification implements Specification {
    @Override
    public String toSqlClause() {
        return "JOIN home_cinema.directors d ON actors.ActorsFullName = d.DirectorsFullName";
    }

    @Override
    public void setParameters(PreparedStatement statement) {
    }
}