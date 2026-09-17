package by.bsu.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                logger.error("Критическая ошибка: Файл database.properties не найден в src/main/resources!");
            } else {
                properties.load(input);
                logger.info("Конфигурация базы данных успешно загружена из db.properties.");

                String driverClass = properties.getProperty("db.driver");
                if (driverClass != null) {
                    Class.forName(driverClass);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Не удалось инициализировать конфигурацию ConnectionFactory: {}", e.getMessage(), e);
        }
    }

    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        if (url == null) {
            logger.error("Ошибка соединения: URL базы данных равен null. Проверь database.properties!");
            throw new SQLException("The url cannot be null (свойства не были загружены)");
        }

        logger.debug("Попытка открытия соединения с БД по адресу: {}", url);
        return DriverManager.getConnection(url, user, password);
    }
}