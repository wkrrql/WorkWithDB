package by.bsu.specification.film;

import by.bsu.specification.Specification;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindFilmsByYearSpecification implements Specification {
    public FindFilmsByYearSpecification() {
    }

    @Override
    public String toSqlClause() {
        return "WHERE YEAR(ReleaseDate) >= YEAR(CURDATE()) - 1 ORDER BY ReleaseDate DESC";
    }

    @Override
    public void setParameters(PreparedStatement statement) throws SQLException {
    }
}