package lesson12.task01;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс с точкой входа. В ходе выполнения программы выбрасывает ошибку OutOfMemoryError и завершается.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Solution {

    public static void main(String[] args) {
        int[] numbers;
        int n = 0;

        try {
            for (int i = 0; i < 1000_000_000; i++) {
                numbers = new int[n];
                n = 10000 * i * i;
                BigDecimal freeMemorySize = new BigDecimal(Runtime.getRuntime().freeMemory()/1048576.00);
                freeMemorySize = freeMemorySize.setScale(2, RoundingMode.HALF_UP);
                System.out.println("Free JVM Memory: " + freeMemorySize + " Mbyte");
            }
        } catch (OutOfMemoryError e) {
            throw e;
        }
    }
}
