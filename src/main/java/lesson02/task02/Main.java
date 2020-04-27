package lesson02.task02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
/**
 * @author Marina_Larionova
 * @version 1.0.0
 * <p>
 * Генератор N случайных чисел, вычисляющий для каждого числа квадратный корень.
 * Если квадрат целой части числа равен исходному числу, это число выводится на экран.
 * Если первоначальные числа отрицательные, в этом случае генерируется исключение.
 */

public class Main {
    /**
     * Приватная переменная nArray - массив целых чисел
     */
    private int[] nArray;

    /**
     * Геттер для переменной nArray, возвращает значение переменной nArray
     * @return nArray
     */
    public int[] getNArray() {
        return nArray;
    }

    /**
     * Сеттер для переменной nArray, устанавливает значение переменной nArray
     * @param nArray
     */
    public void setNArray(int[] nArray) {
        this.nArray = nArray;
    }

    /**
     * Главный метод программы
     *
     * @params args  - параметры командной строки
     */
    public static void main(String[] args) {
        /**
         * Переменная main для создания экземпляра класса Main
         */
        Main main = new Main();
        main.setNArray(main.CreateArray());

        try {
            main.CalculateSquareRoot(main.getNArray());
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, создающий целочисленный массив случайных элементов с длиной, указанной пользователем
     * @return nArray - массив случайных чисел
     */
    private int[] CreateArray() {
        /**
         * Переменная reader для чтения строковых данных с консоли
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        /**
         * Переменная n для перевода строкового значения переменной reader в целое число - длину массива
         */
        int n = 0;
        /**
         * Переменная random для заполнения массива случайными значениями
         */
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
    private void CalculateSquareRoot(int[] array) throws ArithmeticException {
        for (int k : array) {
            if (k < 0) throw new ArithmeticException("Невозможно извлечь корень из отрицательного числа!");
            double s = Math.sqrt(k);
            if (k == (((int) s) * ((int) s))) {
                System.out.println(k);
            }
        }
    }
}
