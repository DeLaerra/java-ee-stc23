package lesson02.task01;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marina_Larionova
 * @version 1.0
 */

public class HelloWorld {

    public static void main(String[] args) {
        List<String> list = CreateArrayList();

        try {
            list.get(10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException");
            e.printStackTrace();
        }
        try {
            System.out.println(list.get(5).split(" "));
        } catch (NullPointerException e1) {
            System.out.println("NullPointerException");
            e1.printStackTrace();
        } finally {
            System.out.println("CustomException");
            throw new CustomException("Custom Message");
        }
    }

    private static List<String> CreateArrayList() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add(String.valueOf(i));
        }
        list.add(null);
        return list;
    }
}
