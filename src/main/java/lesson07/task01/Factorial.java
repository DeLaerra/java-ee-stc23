package lesson07.task01;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс, вычисляющий факториалы.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Factorial implements Callable<BigInteger> {
    private final int VALUE;
    private static final ConcurrentHashMap<Integer, BigInteger> CACHE_MAP = new ConcurrentHashMap<>();

    public Factorial(int VALUE) {
        this.VALUE = VALUE;
    }

    /**
     * Метод, выполняющийся при запуске потока, считающий факториалы и возвращающий значения
     */
    @Override
    public BigInteger call() throws Exception {
        return this.calculateFactorial(VALUE);
    }

    /**
     * Метод, вычисляющий факториалы и добавляющий уже вычисленные значения в коллекцию CACHE_MAP
     *
     * @param value - значение, для которого необходимо вычислить факториал
     * @return факториал
     */
    private BigInteger calculateFactorial(int value) {
        BigInteger factorial = BigInteger.ONE;
        if (value == 0) {
            return factorial;
        } else if (value >= 1) {
            if (CACHE_MAP.get(value) != null) {
                return CACHE_MAP.get(value);
            } else {
                for (int i = 1; i <= value; i++) {
                    factorial = factorial.multiply(BigInteger.valueOf(i));
                    CACHE_MAP.put(i, factorial);
                }
                return factorial;
            }
        } else {
            return BigInteger.valueOf(-1);
        }
    }
}
