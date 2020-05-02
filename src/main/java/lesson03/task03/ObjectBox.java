package lesson03.task03;

import java.util.HashSet;
import java.util.Set;

public class ObjectBox<T> {
    protected Set<Object> objectSet = new HashSet<>();

    public Set<Object> getObjectSet() {
        return objectSet;
    }

    public void setObjectSet(Set<Object> objectSet) {
        this.objectSet = objectSet;
    }

    public ObjectBox() {
    }

    void addObject(T object) {
        objectSet.add(object);
    }

    void deleteObject(T object) {
        objectSet.remove(object);
    }

    void dump() {
        objectSet.forEach(obj-> System.out.print(obj + " "));
    }
}
