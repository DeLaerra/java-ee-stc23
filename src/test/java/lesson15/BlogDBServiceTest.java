package lesson15;

import lesson15.pojo.Sex;
import lesson15.pojo.User;
import org.junit.*;

import java.sql.SQLException;

public class BlogDBServiceTest {
    private static BlogDBService blogDBService;
    private User user;

    @BeforeClass
    public static void setUp() throws Exception {
        blogDBService = new BlogDBService();
        blogDBService.connectToDatabase();

    }

    @Test
    public void insertNewUser() {
        user = new User("pwd", "Rick", "Rick Sanchez", 70, Sex.MALE, "sanchez@mail.com");
        blogDBService.insertNewUser(user);
    }

    @Test
    public void insertUsers() throws SQLException {
        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {
            user = new User("123", "TestUser" + i, "TestName" + i, i * 10, Sex.MALE, "test@mail.com");
            users[i] = user;
        }
        blogDBService.insertUsers(users);
    }

    @Test
    public void editUserName() throws SQLException {
        user = new User("pwd", "Beth", "Beth Sanchez", 35, Sex.FEMALE, "bsmith@mail.com");
        blogDBService.insertNewUser(user);
        blogDBService.editUserName(user, "Beth Smith");
    }

    @AfterClass
    public static void setDown() {
        blogDBService.disconnectFromDatabase();
    }
}