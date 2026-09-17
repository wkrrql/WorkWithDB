package by.bsu.specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Specification {
    String toSqlClause();
    void setParameters(PreparedStatement statement) throws SQLException;
}