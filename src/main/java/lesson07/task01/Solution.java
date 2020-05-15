package lesson07.task01;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Класс, вычисляющий факториалы массива случайных чисел с помощью пула потоков.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Solution {
    private int[] randomNumbers = new int[10];

    public int[] getRandomNumbers() {
        return randomNumbers;
    }

    /**
     * Метод, создающий массив случайных чисел
     */
    public void createRandomArray() {
        Random rnd = new Random();
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = rnd.nextInt(15);
        }
    }

    /**
     * Метод, вычисляющий факториалы массива случайных чисел с помощью пула потоков
     */
    public List<Future<BigInteger>> calculateFactorials() {
        ExecutorService executorService = Executors.newFixedThreadPool(randomNumbers.length);
        List<Callable<BigInteger>> tasks = new ArrayList<>();
        List<Future<BigInteger>> answers = new ArrayList<>();

        Arrays.stream(randomNumbers).forEach(n -> tasks.add(new Factorial(n)));

        try {
            answers = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return answers;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createRandomArray();
        System.out.println(Arrays.toString(solution.getRandomNumbers()));

        solution.calculateFactorials().forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        System.out.println(Factorial.getCacheMap());
    }
}