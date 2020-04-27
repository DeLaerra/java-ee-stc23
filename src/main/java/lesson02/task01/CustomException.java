package lesson02.task01;

/**
 * @author Marina_Larionova
 * @version 1.0.0
 * <p>
 * Класс с исключением.
 */
public class CustomException extends RuntimeException {

    /**
     * Конструктор класса
     * @param message - сообщение, которое необходимо вывести
     */
    public CustomException(String message) {
        super(message);
    }
}
