package lesson22.dao;

import lesson22.pojo.User;

import java.util.List;

public interface UserDao {
    boolean addUser(User user);

    boolean deleteUser(User user);

    boolean editUser(User user, String newNickname, String newPassword, String newEmail);

    User getUserByUuid(String uuid);

    boolean getUserByEmailAndPassword(String email, String password);

    List<User> getAllUsers();
}
