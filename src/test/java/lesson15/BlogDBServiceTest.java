package lesson15;

import lesson15.pojo.Sex;
import lesson15.pojo.User;
import lesson15.pojo.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogDBServiceTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private User mockUser;

    @Mock
    private BlogDBConnector mockBlogDBConnector;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    BlogDBService blogDBService = new BlogDBService();

    @BeforeEach
    public void initMocks() throws SQLException, IOException {
        lenient().when(mockBlogDBConnector.getConnection()).thenReturn(mockConnection);
        lenient().when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        lenient().when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }


    @Test
    public void shouldAddNewUser() throws IOException {
        mockUser = new UserBuilder()
                .setPassword("pwd")
                .setNickname("Rick")
                .setName("Rick Sanchez")
                .setAge(70)
                .setSex(Sex.MALE)
                .setEmail("sanchez@mail.com")
                .createUser();
        blogDBService.addNewUser(mockUser);
    }

    @Test
    public void shouldAddUsers() throws SQLException, IOException {
        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {
            mockUser = new UserBuilder()
                    .setPassword("123")
                    .setNickname("TestUser" + i)
                    .setName("TestName" + i).setAge(i * 10)
                    .setSex(Sex.MALE)
                    .setEmail("test@mail.com")
                    .createUser();
            users[i] = mockUser;
        }
        blogDBService.addUsers(users);
    }

    @Test
    public void shouldEditUserName() throws SQLException, IOException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(3)).thenReturn("Beth");
        when(mockResultSet.getString(4)).thenReturn("Beth Sanchez").thenReturn("Beth Smith");
        when(mockResultSet.getInt(5)).thenReturn(35);
        when(mockResultSet.getString(6)).thenReturn("female");
        when(mockResultSet.getString(7)).thenReturn("test@email.com");
        when(mockResultSet.getBoolean(8)).thenReturn(false);
        blogDBService.editUserName(mockUser, "Beth Smith");
    }

    @Test
    public void shouldSelectUserByUuid() throws SQLException, IOException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(3)).thenReturn("Beth");
        when(mockResultSet.getString(4)).thenReturn("Beth Sanchez");
        when(mockResultSet.getInt(5)).thenReturn(35);
        when(mockResultSet.getString(6)).thenReturn("female");
        when(mockResultSet.getString(7)).thenReturn("test@email.com");
        when(mockResultSet.getBoolean(8)).thenReturn(false);
        blogDBService.selectUserByUuid(mockUser.getUuid());
    }

    @Test
    public void shouldThrowIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            User[] users = new User[600];
            for (int i = 0; i < users.length; i++) {
                mockUser = new UserBuilder()
                        .setPassword("123")
                        .setNickname("TestUser" + i)
                        .setName("TestName" + i).setAge(i * 10)
                        .setSex(Sex.MALE)
                        .setEmail("test@mail.com")
                        .createUser();
                users[i] = mockUser;
            }
            blogDBService.addUsers(users);
        });
        String expectedMessage = "Слишком много запросов, укажите меньше пользователей!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}