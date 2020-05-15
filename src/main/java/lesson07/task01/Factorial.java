package lesson07.task01;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс, вычисляющий факториалы.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Factorial implements Callable {
    private final int n;
    private static ConcurrentHashMap<Integer, BigInteger> cacheMap = new ConcurrentHashMap<>();

    public static Map<Integer, BigInteger> getCacheMap() {
        return cacheMap;
    }

    public Factorial(int n) {
        this.n = n;
    }

    /**
     * Метод, вычисляющий факториалы и добавляющий уже вычисленные значения в коллекцию cacheMap
     *
     * @param value - значение, для которого необходимо вычислить факториал
     * @return факториал
     */
    private BigInteger calculateFactorial(int value) {
        BigInteger factorial = BigInteger.ONE;
        if (value == 0) {
            return factorial;
        } else if (value >= 1) {
            if (cacheMap.get(value) != null) {
                return cacheMap.get(value);
            } else {
                for (int i = 1; i <= value; i++) {
                    factorial = factorial.multiply(BigInteger.valueOf(i));
                    cacheMap.put(i, factorial);
                }
                return factorial;
            }
        } else {
            return BigInteger.valueOf(-1);
        }
    }

    /**
     * Метод, выполняющийся при запуске потока, считающий факториалы и возвращающий значения
     */
    @Override
    public BigInteger call() throws Exception {
        return this.calculateFactorial(n);
    }
}
