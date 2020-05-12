package lesson07.task01;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    public void calculateFactorials() {
        ExecutorService es = Executors.newCachedThreadPool();

        for (int randomNumber : randomNumbers) {
            Factorial factorial = new Factorial(randomNumber);
            Thread t = new Thread(factorial);
            es.execute(t);
        }

        try {
            es.shutdown();
            es.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createRandomArray();
        System.out.println(Arrays.toString(solution.getRandomNumbers()));
        solution.calculateFactorials();
        System.out.println(Factorial.getCacheMap());
    }
}