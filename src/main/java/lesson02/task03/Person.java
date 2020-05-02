package lesson02.task03;

/**
 * Класс для хранения данных о людях
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Person {
    private int age;
    private Sex sex;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name, int age, Sex sex) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " " + age + " " + sex;
    }
}
