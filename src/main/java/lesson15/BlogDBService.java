package lesson15;

import lesson15.pojo.User;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

/**
 * Класс для обслуживания БД blog_user
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class BlogDBService {
    private Connection connection;
    private static final String SQL_INSERT_USER = "INSERT INTO blog_user (uuid, password, nickname, name, age, sex, email, moderator) " +
            "VALUES(?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER_NAME = "UPDATE blog_user SET name = ? WHERE uuid = ?";
    private static final String SQL_SELECT_USER = "SELECT * FROM blog_user WHERE uuid = ?";

    /**
     * Метод для подключения к БД
     *
     * @throws SQLException
     * @throws IOException
     */
    public void connectToDatabase() throws SQLException, IOException {
        connection = BlogDBConnector.getConnection();
    }

    /**
     * Метод для добавления нового пользователя в БД
     *
     * @param newUser - новый пользователь
     */
    public void insertNewUser(User newUser) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для пакетного добавления новых пользователей в БД
     *
     * @param users - массив пользователей
     * @throws SQLException
     */
    public void insertUsers(User... users) throws SQLException {
        if (users.length < 500) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
                connection.setAutoCommit(false);
                connection.rollback();
                for (int i = 0; i < users.length; i++) {
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
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
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
    public void editUserName(User user, String newName) throws SQLException {
        Savepoint beforeUpdateSaving = null;
        try (PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE_USER_NAME)) {
            connection.setAutoCommit(false);
            connection.rollback();
            System.out.println("До изменения:");
            selectUserByUuid(user.getUuid());
            beforeUpdateSaving = connection.setSavepoint();

            psUpdate.setString(1, newName);
            psUpdate.setString(2, user.getUuid());
            psUpdate.executeUpdate();
            connection.commit();

            System.out.println("После изменения:");
            selectUserByUuid(user.getUuid());
        } catch (SQLException e) {
            connection.rollback(beforeUpdateSaving);
            e.printStackTrace();
        }
    }

    /**
     * Метод для выборки пользователей из БД
     *
     * @param uuid - критерий выбора
     */
    private void selectUserByUuid(String uuid) {
        try (PreparedStatement psSelect = connection.prepareStatement(SQL_SELECT_USER)) {
            psSelect.setString(1, uuid);
            ResultSet resultSet = psSelect.executeQuery();
            while (resultSet.next()) {
                String nickname = resultSet.getString(3);
                String name = resultSet.getString(4);
                int age = resultSet.getInt(5);
                String sex = resultSet.getString(6);
                String email = resultSet.getString(7);
                boolean isModerator = resultSet.getBoolean(8);
                System.out.println(uuid + ", " + nickname + ", " + name + ", " + age + ", " + sex + ", " + email + ", " + isModerator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для отключения от БД
     *
     * @throws SQLException
     * @throws IOException
     */
    public void disconnectFromDatabase() {
        Optional.ofNullable(connection).ifPresent(connection1 -> {
            try {
                connection1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}