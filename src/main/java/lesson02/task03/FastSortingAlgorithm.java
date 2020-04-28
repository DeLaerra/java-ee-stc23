package lesson02.task03;

import java.util.List;

/**
 * Класс, реализующий быструю сортировку списка объектов класса Person
 *
 * @see Sortable
 * @see Person
 */
public class FastSortingAlgorithm implements Sortable {
    /**
     * Метод, реализующий быструю сортировку списка объектов класса Person
     *
     * @param personArrayList - список объектов для сортировки
     * @param low             - нижний индекс списка
     * @param high            - верхний индекс списка
     */
    public void SortPersonArrayList(List<Person> personArrayList, int low, int high) {
        /**
         * Если нижний индекс становится больше верхнего, значит необходимо закончить сортировку
         */
        if (low > high)
            return;
        /**
         * Выбор опорного элемента opora из середины списка personArrayList
         */
        int middle = low + (high - low) / 2;
        Person opora = personArrayList.get(middle);
        /**
         * Переменные i и j - счётчики циклов
         */
        int i = low, j = high;
        /**
         * Сравнение объектов класса Person с опорным элементом, начиная с верхней (j = high) и нижней (i = low)
         * границы списка personArrayList.
         */
        while (i <= j) {
            while (compare(personArrayList.get(i), opora) < 0) {
                i++;
            }
            while (compare(personArrayList.get(j), opora) > 0) {
                j--;
            }
            /**
             * Объекты класса Person в списке personArrayList меняются местами: те, которые меньше опорного,
             * уходят налево от опорного, а которые больше - направо от опорного
             */
            if (i <= j) {
                Person temp = personArrayList.get(i);
                personArrayList.set(i, personArrayList.get(j));
                personArrayList.set(j, temp);
                i++;
                j--;
            }
        }
        /**
         * Повторные сортировки списка personArrayList, смена опорного элемента
         */
        if (low < j)
            SortPersonArrayList(personArrayList, low, j);
        if (high > i)
            SortPersonArrayList(personArrayList, i, high);
    }

}






