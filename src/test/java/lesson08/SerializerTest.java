package lesson08;

import lesson05.task01.Person;
import lesson05.task01.Sex;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class SerializerTest {
    private Serializer serializer = new Serializer();
    private String refPathPerson = "src/test/resources/lesson08/" + Person.class.getName() + ".json";
    private Person person = new Person("John Smith", 27, Sex.MAN);


    @Test
    public void serialize() {
        try {
            serializer.serialize(person, refPathPerson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deSerialize() {
        try {
            serializer.deSerialize(refPathPerson);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}