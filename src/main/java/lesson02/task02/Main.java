package lesson02.task02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Генератор N случайных чисел, вычисляющий для каждого числа квадратный корень.
 * Если квадрат целой части числа равен исходному числу, это число выводится на экран.
 * Если первоначальные числа отрицательные, в этом случае генерируется исключение.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */

public class Main {
    private int[] nArray;

    public int[] getNArray() {
        return nArray;
    }

    public void setNArray(int[] nArray) {
        this.nArray = nArray;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setNArray(main.createArray());

        try {
            main.calculateSquareRoot(main.getNArray());
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, создающий целочисленный массив случайных элементов с длиной, указанной пользователем
     *
     * @return nArray - массив случайных чисел
     */
    private int[] createArray() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = 0;
        Random random = new Random();

        try {
            System.out.println("Введите число:");
            n = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] nArray = new int[n];
        for (int i = 0; i < n; i++) {
            nArray[i] = (random.nextInt(1000) - 10);
        }
        return nArray;
    }

    /**
     * Метод для вычисления квадратного корня элементов массива
     *
     * @param array - целочисленный массив
     * @throws ArithmeticException - исключение при попытке извлечь корень из отрицательного числа
     */
    private void calculateSquareRoot(int[] array) throws ArithmeticException {
        for (long k : array) {
            if (k < 0) throw new ArithmeticException("Невозможно извлечь корень из отрицательного числа!");
            double s = Math.sqrt(k);
            if (k == (((int) s) * ((int) s))) {
                System.out.println(k);
            }
        }
    }
}
