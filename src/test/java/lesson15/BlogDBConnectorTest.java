package lesson15;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BlogDBConnectorTest {
    private Connection connection;

    @Mock
    private Path mockDatabaseProperties;

    @InjectMocks
    private BlogDBConnector instance;

    @BeforeEach
    void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = BlogDBConnector.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void shouldGetInstance() {
        instance = BlogDBConnector.getInstance();
        assertNotNull(instance);
    }

    @Test
    void shouldGetConnection() throws SQLException, IOException {
        instance = BlogDBConnector.getInstance();
        connection = instance.getConnection();
        assertTrue(connection.isValid(1));
        connection.close();
    }

    @Test
    void shouldDisconnectFromDatabase() throws SQLException, IOException {
        instance = BlogDBConnector.getInstance();
        connection = instance.getConnection();
        assertTrue(connection.isValid(1));
        instance.disconnectFromDatabase(connection);
        assertTrue(connection.isClosed());
    }

    @Test
    void shouldThrowIOException() {
        Exception exception = assertThrows(IOException.class, () -> connection = instance.getConnection());
        String expectedMessage = "Попытка чтения файла настроек БД неудачна!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}