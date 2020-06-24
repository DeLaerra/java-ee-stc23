package lesson15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

/**
 * Класс для подключения к БД PostgreSQL blog_user
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class BlogDBConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogDBConnector.class);

    private BlogDBConnector() {
    }

    /**
     * Метод для подключения к БД. Использует файл свойств database.properties
     *
     * @return коннект к БД
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getConnection() {
        Properties properties = new Properties();
        Connection connection = null;
        try {
            LOGGER.debug("Чтение файла настроек БД.");
            FileInputStream fis = new FileInputStream("src/main/resources/lesson15/database.properties");
            properties.load(fis);
        } catch (IOException e) {
            LOGGER.error("Попытка чтения файла настроек БД неудачна!", e);
            LOGGER.trace("",e);
        }
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        LOGGER.debug("url: " + url + "; user: " + user);
        try {
            connection = DriverManager.getConnection(url, user, password);
            LOGGER.debug("Подключение к БД " + connection.getCatalog() + " успешно.");
        } catch (SQLException e) {
            LOGGER.error("Попытка подключения к БД неудачна!", e);
            LOGGER.trace("",e);
        }
        return connection;
    }

    /**
     * Метод для отключения от БД
     *
     * @throws SQLException
     * @throws IOException
     */
    public static void disconnectFromDatabase(Connection connection) {
        LOGGER.debug("Попытка отключения от БД.");
        Optional.ofNullable(connection).ifPresent(connection1 -> {
            try {
                connection1.close();
                LOGGER.debug("Отключение от БД успешно.");
            } catch (SQLException e) {
                LOGGER.error("Попытка отключения от БД неудачна!");
                LOGGER.trace("",e);
            }
        });
    }

}
