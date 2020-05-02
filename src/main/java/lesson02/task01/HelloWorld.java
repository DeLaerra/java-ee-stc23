package lesson02.task01;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс с точкой входа. В ходе выполнения программы выбрасывает исключения и завершается с ошибкой.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */

public class HelloWorld {

    public static void main(String[] args) {
        List<String> list = createArrayList();
        /**
         * Вызов IndexOutOfBoundsException при получения элемента с индексом за пределами массива
         */
        try {
            list.get(10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException");
            e.printStackTrace();
        }
        /**
         * Вызов NullPointerException при попытке работы со значением null
         */
        try {
            System.out.println(list.get(5).split(" "));
        } catch (NullPointerException e1) {
            System.out.println("NullPointerException");
            e1.printStackTrace();
            /**
             * Вызов CustomException с сообщением "Custom Message"
             */
        } finally {
            System.out.println("CustomException");
            throw new CustomException("Custom Message");
        }
    }

    /**
     * Метод для создания списка значений
     *
     * @return list - список строк
     */
    private static List<String> createArrayList() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add(String.valueOf(i));
        }
        list.add(null);
        return list;
    }
}
