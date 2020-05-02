package lesson03.task02;

import java.util.ArrayList;
import java.util.List;

public class ObjectBox {
    private List<Object> objectList = new ArrayList<>();

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    public ObjectBox(Object object) {
       addObject(object);
    }

    void addObject(Object object) {
        objectList.add(object);
    }

    void deleteObject(Object object) {
        objectList.remove(object);
    }

    void dump(List<Object> objectList) {
        objectList.forEach(obj-> System.out.print(obj + " "));
    }
}
