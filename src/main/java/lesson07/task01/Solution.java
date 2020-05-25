package lesson07.task01;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Класс, вычисляющий факториалы чисел с помощью пула потоков.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Solution {
    /**
     * Метод, вычисляющий факториалы чисел с помощью пула потоков
     *
     * @param numbers - лист случайных чисел
     * @return - факториалы
     * @throws InterruptedException
     */
    public List<Future<BigInteger>> calculateFactorials(List<Integer> numbers) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(numbers.size());

        final List<Future<BigInteger>> answers = executorService.invokeAll(numbers
                .stream()
                .map(Factorial::new)
                .collect(Collectors.toList()));

        return answers;
    }
}