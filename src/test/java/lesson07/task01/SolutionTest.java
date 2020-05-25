package lesson07.task01;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SolutionTest {
    private static List<Integer> randomList = new ArrayList<>();

    @BeforeClass
    public static void shouldCreateRandomList() {
        randomList = ThreadLocalRandom.current()
                .ints(10, 0, 16)
                .boxed()
                .collect(Collectors.toList());
    }


    @Test
    public void shouldCalculateFactorials() throws InterruptedException {
        Solution solution = new Solution();
        solution.calculateFactorials(randomList).forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        randomList.forEach(obj -> System.out.print(obj + ", "));
    }
}