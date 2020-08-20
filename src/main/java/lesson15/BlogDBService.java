package lesson15;

import lesson15.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

/**
 * Класс для обслуживания БД blog_user
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class BlogDBService {
    private static final String SQL_INSERT_USER = "INSERT INTO blog_user (user_uuid, password, nickname, name, age, sex, email, moderator) " +
            "VALUES(?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER_NAME = "UPDATE blog_user SET name = ? WHERE user_uuid = ?";
    private static final String SQL_SELECT_USER = "SELECT * FROM blog_user WHERE user_uuid = ?";
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogDBService.class);
    private BlogDBConnector blogDBConnector = BlogDBConnector.getInstance();
    private ResultSet resultSet;

    /**
     * Метод для добавления нового пользователя в БД
     *
     * @param newUser - новый пользователь
     */
    public void addNewUser(User newUser) throws IOException {
        LOGGER.info("Добавление нового пользователя: " + newUser.toString());
        Connection connection = blogDBConnector.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, newUser.getUuid());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getNickname());
            preparedStatement.setString(4, newUser.getName());
            preparedStatement.setInt(5, newUser.getAge());
            preparedStatement.setString(6, newUser.getSex().toString());
            preparedStatement.setString(7, newUser.getEmail());
            preparedStatement.setBoolean(8, newUser.isModerator());
            preparedStatement.executeUpdate();
            LOGGER.info("Пользователь " + newUser.toString() + " добавлен.");
        } catch (SQLException e) {
            LOGGER.error("Попытка добавления нового пользователя неудачна!", e);
            LOGGER.trace("", e);
        } finally {
            blogDBConnector.disconnectFromDatabase(connection);
        }
    }

    /**
     * Метод для пакетного добавления новых пользователей в БД
     *
     * @param users - массив пользователей
     * @throws SQLException
     */
    public void addUsers(User... users) throws SQLException, IOException, IllegalArgumentException {
        if (users.length < 500) {
            Connection connection = blogDBConnector.getConnection();
            LOGGER.warn("Добавление новых пользователей. Количество пользователей: " + users.length);
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
                connection.setAutoCommit(false);
                connection.rollback();
                for (int i = 0; i < users.length; i++) {
                    LOGGER.debug("Добавление пользователя: " + users[i].toString());
                    preparedStatement.setString(1, users[i].getUuid());
                    preparedStatement.setString(2, users[i].getPassword());
                    preparedStatement.setString(3, users[i].getNickname());
                    preparedStatement.setString(4, users[i].getName());
                    preparedStatement.setInt(5, users[i].getAge());
                    preparedStatement.setString(6, users[i].getSex().toString());
                    preparedStatement.setString(7, users[i].getEmail());
                    preparedStatement.setBoolean(8, users[i].isModerator());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
                Arrays.stream(users).forEach(x -> LOGGER.info("Добавлен пользователь:" + x.toString()));
            } catch (SQLException e) {
                connection.rollback();
                LOGGER.error("Попытка добавления новых пользователей неудачна!", e);
                LOGGER.trace("", e);
            } finally {
                blogDBConnector.disconnectFromDatabase(connection);
            }
        } else {
            throw new IllegalArgumentException("Слишком много запросов, укажите меньше пользователей!");
        }
    }

    /**
     * Метод для изменения имени пользователя в БД
     *
     * @param user    - пользователь, имя которого необходимо сменить
     * @param newName - новое имя
     * @throws SQLException
     */
    public void editUserName(User user, String newName) throws SQLException, IOException {
        Savepoint beforeUpdateSaving = null;
        Connection connection = blogDBConnector.getConnection();
        LOGGER.warn("Изменение имени пользователя " + user.toString() + " на " + newName);
        try (PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE_USER_NAME)) {
            connection.setAutoCommit(false);
            connection.rollback();
            LOGGER.info("Данные пользователя до изменения:");
            selectUserByUuid(user.getUuid());
            beforeUpdateSaving = connection.setSavepoint();

            psUpdate.setString(1, newName);
            psUpdate.setString(2, user.getUuid());
            psUpdate.executeUpdate();
            connection.commit();

            LOGGER.info("Данные пользователя после изменения:");
            selectUserByUuid(user.getUuid());
        } catch (SQLException e) {
            connection.rollback(beforeUpdateSaving);
            LOGGER.error("Попытка изменения имени пользователя  неудачна", e);
            LOGGER.trace("", e);
        } finally {
            blogDBConnector.disconnectFromDatabase(connection);
        }
    }

    /**
     * Метод для выборки пользователей из БД
     *
     * @param uuid - критерий выбора
     */
    public void selectUserByUuid(String uuid) throws IOException {
        Connection connection = blogDBConnector.getConnection();
        try (PreparedStatement psSelect = connection.prepareStatement(SQL_SELECT_USER)) {
            psSelect.setString(1, uuid);
            resultSet = psSelect.executeQuery();
            while (resultSet.next()) {
                String nickname = resultSet.getString(3);
                String name = resultSet.getString(4);
                int age = resultSet.getInt(5);
                String sex = resultSet.getString(6);
                String email = resultSet.getString(7);
                boolean isModerator = resultSet.getBoolean(8);
                LOGGER.info("Пользователь: " + uuid + ", " + nickname + ", " + name + ", " + age + ", " + sex + ", " +
                        email + ", " + isModerator);
            }
        } catch (SQLException e) {
            LOGGER.error("Выполнение запроса SQL_SELECT закончилось неудачей", e);
            LOGGER.trace("", e);
        } finally {
            blogDBConnector.disconnectFromDatabase(connection);
        }
    }


}