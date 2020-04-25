package lesson02.task02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;


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
        main.setNArray(main.CreateArray());
        main.CalculateSquareRoot(main.getNArray());
    }

    private int[] CreateArray() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = 0;
        Random random = new Random();

        try {
            System.out.println("Введите число:");
            n = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] nArray = new int[n];
        for (int i = 0; i < n; i++) {
            nArray[i] = (random.nextInt(1000) - 10);
        }
        return nArray;
    }

    private void CalculateSquareRoot(int[] array) {
        for (int k : array) {
            if (k < 0) throw new ArithmeticException("Невозможно извлечь корень из отрицательного числа!");
            double s = Math.sqrt(k);
            if (k == (((int) s) * ((int) s))) {
                System.out.println(k);
            }
        }
    }
}
