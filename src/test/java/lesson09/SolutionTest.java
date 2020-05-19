package lesson09;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.*;

public class SolutionTest {
    private static final InputStream systemIn = System.in;
    private static ByteArrayInputStream testIn;

    @BeforeClass
    public static void provideInput() {
        final String testString = "System.out.println(\"New doWork\");";
        testIn = new ByteArrayInputStream(testString.getBytes());
        System.setIn(testIn);
    }

    @AfterClass
    public static void restoreSystemInputOutput() {
        System.setIn(systemIn);
    }

    @Test
    public void shouldRunCustomClassLoader() throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        Solution solution = new Solution();
        solution.runCustomClassLoader();
    }
}