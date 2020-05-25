package lesson09;

/**
 * Класс, который компилируется в режиме runtime, а затем подгружается в программу с помощью
 * кастомного загрузчика CustomClassLoader
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class SomeClass implements Worker {
    /**
     * Метод, который после ввода с консоли исполняется в режиме runtime
     */
    @Override
    public void doWork() {
        System.out.println("Old doWork");
    }
}
