package lesson02.task03;

import java.util.Comparator;

/**
 * Функциональный интерфейс, наследующий интерфейс Comparator<Person> и переопределяющий метод compare(Person o1, Person o2)
 * как дефолтный метод
 */
public interface Sortable extends Comparator<Person> {
    /**
     * Метод, сортирующий экземпляры класса Person по следующим правилам:
     * - первые идут мужчины
     * - выше в списке тот, кто более старший
     * - имена сортируются по алфавиту
     *
     * @param o1 - экземпляр класса Person
     * @param o2 - экземпляр класса Person
     * @return - возвращает ноль, если оба объекта равны
     */
    default int compare(Person o1, Person o2) {
        int value1 = o1.getSex().compareTo(o2.getSex());
        if (value1 == 0) {
            Integer age1 = o1.getAge();
            Integer age2 = o2.getAge();
            int value2 = age2.compareTo(age1);
            if (value2 == 0) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return value2;
            }
        }
        return value1;
    }
}
