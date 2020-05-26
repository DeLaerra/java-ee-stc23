package lesson12.task02;

import javassist.CannotCompileException;
import javassist.ClassPool;

/**
 * Класс с точкой входа. В ходе выполнения программы выбрасывает ошибку java.lang.OutOfMemoryError: Metaspace и завершается.
 * Для ускорения процесса можно установить флаг -XX:MaxMetaspaceSize=16m
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Solution {

    public static void main(String[] args) throws CannotCompileException {
        for (int i = 0; i < 100_000_000; i++) {
            String className = "TestClass" + i;
            ClassPool pool = ClassPool.getDefault();
            Class TestClass = pool.makeClass(className).toClass();
        }
    }
}
