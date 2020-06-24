package lesson15;

import lesson15.pojo.Sex;
import lesson15.pojo.User;
import lesson15.pojo.UserBuilder;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

public class BlogDBServiceTest {
    private static BlogDBService blogDBService;
    private User user;

    @BeforeClass
    public static void setUp() throws Exception {
        blogDBService = new BlogDBService();
    }

    @Test
    public void insertNewUser() {
        user = new UserBuilder().setPassword("pwd").setNickname("Rick").setName("Rick Sanchez").setAge(70).setSex(Sex.MALE).setEmail("sanchez@mail.com").createUser();
        blogDBService.addNewUser(user);
    }

    @Test
    public void insertUsers() throws SQLException {
        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {
            user = new UserBuilder().setPassword("123").setNickname("TestUser" + i).setName("TestName" + i).setAge(i * 10).setSex(Sex.MALE).setEmail("test@mail.com").createUser();
            users[i] = user;
        }
        blogDBService.addUsers(users);
    }

    @Test
    public void editUserName() throws SQLException {
        user = new UserBuilder().setPassword("pwd").setNickname("Beth").setName("Beth Sanchez").setAge(35).setSex(Sex.FEMALE).setEmail("bsmith@mail.com").createUser();
        blogDBService.addNewUser(user);
        blogDBService.editUserName(user, "Beth Smith");
    }

}