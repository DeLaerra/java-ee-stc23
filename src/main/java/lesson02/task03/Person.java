package lesson02.task03;

/**
 * Класс для хранения данных о людях
 */
public class Person {
    /**
     * Приватные переменные для хранения данных об имени, возрасте и поле
     */
    private int age;
    private Sex sex;
    private String name;

    /**
     * Геттер для переменной age, возвращает значение переменной age
     *
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Сеттер для переменной age, устанавливает значение переменной age
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Геттер для переменной sex, возвращает значение переменной sex
     *
     * @return sex
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Сеттер для переменной sex, устанавливает значение переменной sex
     *
     * @param sex
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * Геттер для переменной name, возвращает значение переменной name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Сеттер для переменной name, устанавливает значение переменной name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Конструктор класса Person
     *
     * @param name
     * @param age
     * @param sex
     */
    public Person(String name, int age, Sex sex) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    /**
     * Переопределенный метод toString() для класса Person
     *
     * @return name + " " + age + " " + sex
     */
    @Override
    public String toString() {
        return name + " " + age + " " + sex;
    }
}
