package lesson06.task01;

import java.io.IOException;

/**
 * Класс с точкой входа
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();

        try {
            fileReader.readInputFile(fileReader.pathInputFile);
            fileReader.textList.forEach(System.out::println);
            fileReader.saveResultFile(fileReader.pathResultFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
