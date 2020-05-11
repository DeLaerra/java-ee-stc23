package lesson03.task03;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс для работы с объектами класса Object
 *
 * @param <T> extends Number
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class ObjectBox<T> {
    Set<T> objectSet = new HashSet<>();

    public Set<T> getObjectSet() {
        return objectSet;
    }

    public void addObject(T object) {
        objectSet.add(object);
    }

    public void deleteObject(T object) {
        objectSet.remove(object);
    }

    public void dump() {
        objectSet.forEach(obj -> System.out.print(obj + " "));
    }
}
