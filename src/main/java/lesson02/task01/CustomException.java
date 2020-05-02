package lesson02.task01;

/**
 * Класс с исключением.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
