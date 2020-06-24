package lesson15;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс для подключения к БД PostgreSQL blog_user
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class BlogDBConnector {
    /**
     * Метод для подключения к БД. Использует файл свойств database.properties
     *
     * @return коннект к БД
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/lesson15/database.properties");
        properties.load(fis);
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
