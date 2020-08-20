package lesson22.connectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для подключения к БД PostgreSQL blog_user
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class BlogDBConnector implements ConnectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogDBConnector.class);

    /**
     * Метод для подключения к БД. Использует файл свойств database.properties
     *
     * @return коннект к БД
     */
    @Override
    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/blog_db", "Rin", "Rin");
            LOGGER.info("Подключение к БД " + connection.getCatalog() + " успешно.");
        } catch (SQLException e) {
            LOGGER.error("Попытка подключения к БД неудачна!", e);
        }
        return connection;
    }
}
