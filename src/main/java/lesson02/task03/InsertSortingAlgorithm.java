package lesson02.task03;

import java.util.List;

/**
 * Класс, реализующий сортировку вставками списка объектов класса Person
 *
 * @author Marina_Larionova
 * @version 1.0.0
 * @see Sortable
 * @see Person
 */
public class InsertSortingAlgorithm implements Sortable {
    /**
     * Метод, реализующий сортировку вставками списка объектов класса Person
     *
     * @param personArrayList
     */
    public void sortPersonArrayList(List<Person> personArrayList) {
/**
 * Цикл для прохода по элементам списка
 */
        for (int i = 1; i < personArrayList.size(); i++) {
            /**
             * Переменная person - объект из списка personArrayList с индексом i, равным индексу текущей итерации цикла
             */
            Person person = personArrayList.get(i);
            /**
             * Переменная j - индекс предыдущего элемента из списка personArrayList
             */
            int j = i - 1;
            /**
             * Берем элемент из неотсортированной части списка и ставим его на нужное место в отсортированной
             */
            while (j >= 0 && compare(person, personArrayList.get(j)) == 0) {
                personArrayList.set(j + 1, personArrayList.get(j));
                j--;
            }
            personArrayList.set(j + 1, person);
        }
    }
}

