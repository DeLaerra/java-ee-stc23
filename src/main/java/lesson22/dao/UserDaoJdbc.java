package lesson22.dao;

import lesson22.connectionManager.ConnectionManager;
import lesson22.pojo.User;
import lesson22.pojo.UserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EJB
public class UserDaoJdbc implements UserDao {
    private final Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJdbc.class.getName());
    private static final String INSERT_PERSON_SQL =
            "INSERT INTO blog_user (user_uuid, nickname, password, email) values (?, ?, ?, ?)";
    private static final String SELECT_FROM_BLOG_USER = "SELECT * FROM blog_user WHERE user_uuid = ?";
    private static final String SELECT_FROM_BLOG_USER_BY_EMAIL = "SELECT * FROM blog_user WHERE (email = ? AND password = ?)";
    private static final String SELECT_ALL_BLOG_USERS = "SELECT * FROM blog_user";
    private static final String UPDATE_PERSON_SQL =
            "UPDATE blog_user SET password=?, nickname=?, email=? WHERE user_uuid=?";
    private static final String DELETE_PERSON_SQL = "DELETE FROM blog_user WHERE user_uuid=?";

    @Inject
    public UserDaoJdbc(ConnectionManager connectionManager) {
        connection = connectionManager.getConnection();
    }

    @Override
    public boolean addUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PERSON_SQL)) {
            preparedStatement.setString(1, user.getUuid());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.executeUpdate();
            LOGGER.info("Пользователь " + user.getUuid() + " " + user.getNickname() + " добавлен.");
        } catch (SQLException e) {
            LOGGER.error("Попытка добавления нового пользователя неудачна!", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PERSON_SQL)) {
            preparedStatement.setString(1, user.getUuid());
            preparedStatement.executeUpdate();
            LOGGER.info("Пользователь " + user.getUuid() + " " + user.getNickname() + " удален.");
        } catch (SQLException e) {
            LOGGER.error("Попытка удаления пользователя " + user.getUuid() + " " + user.getNickname() + " неудачна!", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean editUser(User user, String newNickname, String newPassword, String newEmail) {
        try (PreparedStatement psUpdate = connection.prepareStatement(UPDATE_PERSON_SQL)) {
            psUpdate.setString(1, newPassword);
            psUpdate.setString(2, newNickname);
            psUpdate.setString(3, newEmail);
            psUpdate.setString(4, user.getUuid());
            psUpdate.executeUpdate();

            LOGGER.info("Пользователь " + user.getUuid() + " " + user.getNickname() + " изменен.");
        } catch (SQLException e) {
            LOGGER.error("Попытка изменения пользователя " + user.getUuid() + " " + user.getNickname() + " неудачна", e);
            return false;
        }
        return true;
    }

    @Override
    public User getUserByUuid(String uuid) {
        try (PreparedStatement psSelect = connection.prepareStatement(SELECT_FROM_BLOG_USER)) {
            psSelect.setString(1, uuid);
            try (ResultSet resultSet = psSelect.executeQuery()) {
                if (resultSet.next()) {
                    return new UserBuilder()
                            .setUuid(resultSet.getString(1))
                            .setPassword(resultSet.getString(2))
                            .setNickname(resultSet.getString(3))
                            .setEmail(resultSet.getString(7))
                            .createUser();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Выполнение запроса SELECT_FROM_BLOG_USER закончилось неудачей", e);
        }
        return null;
    }

    @Override
    public boolean getUserByEmailAndPassword(String email, String password) {
        boolean isExists;
        try (PreparedStatement psSelect = connection.prepareStatement(SELECT_FROM_BLOG_USER_BY_EMAIL)) {
            psSelect.setString(1, email);
            psSelect.setString(2, password);
            try (ResultSet resultSet = psSelect.executeQuery()) {
                if (resultSet.next()) {
                    isExists = true;
                }
                else isExists = false;
            }
        } catch (SQLException e) {
            LOGGER.error("Выполнение запроса SELECT_FROM_BLOG_USER_BY_EMAIL закончилось неудачей", e);
            isExists = false;
        }
        return isExists;
    }


    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BLOG_USERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new UserBuilder()
                            .setUuid(resultSet.getString(1))
                            .setNickname(resultSet.getString(3))
                            .setPassword(resultSet.getString(2))
                            .setEmail(resultSet.getString(7)).createUser();
                    result.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка на уровне DAO!!", e);
        }
        return result;
    }
}
