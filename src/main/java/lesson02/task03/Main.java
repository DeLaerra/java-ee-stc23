package lesson02.task03;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * @author Marina_Larionova
 * @version 1.0.0
 * <p>
 * Программа выводит на экран отсортированный список объектов класса Person и время работы каждого алгоритма сортировки.
 * Если имена людей и возраст совпадают, в программе возникает исключение CustomExceptionMessage.
 * @see Person
 * @see CustomExceptionMessage
 */
public class Main {
    /**
     * Главный метод программы
     *
     * @param args - параметры командной строки
     */
    public static void main(String[] args) {
        /**
         * Переменная personList для списка объектов класса Person
         */
        List<Person> personList = null;
        /**
         * Заполнение списка personList и перехват исключения CustomExceptionMessage
         */
        try {
            personList = CreatePersonArrayList();
        } catch (CustomExceptionMessage customExceptionMessage) {
            customExceptionMessage.printStackTrace();
        }

        FastSortingAlgorithm fastSortingAlgorithm = new FastSortingAlgorithm();
        InsertSortingAlgorithm insertSortingAlgorithm = new InsertSortingAlgorithm();
        /**
         * Переменные low и high - минимальный и максимальный индексы для быстрой сортировки
         */
        int low = 0;
        int high = personList.size() - 1;

        /**
         * Переменные beforeFirstSorting и afterFirstSorting - для замера времени быстрой сортировки
         */
        long beforeFirstSorting = System.nanoTime();
        fastSortingAlgorithm.SortPersonArrayList(personList, low, high);
        long afterFirstSorting = System.nanoTime() - beforeFirstSorting;
        /**
         * Переменные beforeSecondSorting и afterSecondSorting - для замера времени сортировки вставкой
         */
        long beforeSecondSorting = System.nanoTime();
        insertSortingAlgorithm.SortPersonArrayList(personList);
        long afterSecondSorting = System.nanoTime() - beforeSecondSorting;
        /**
         * Вывод отсортированного списка на экран
         */
        personList.forEach(person -> System.out.println(person));
        System.out.println();
        System.out.println("Время работы алгоритма быстрой сортировки: " + afterFirstSorting + " ns ");
        System.out.println("Время работы алгоритма сортировки вставкой: " + afterSecondSorting + " ns");
    }

    /**
     * Метод, создающий список из 11000 объектов класса Person
     *
     * @return personList
     * @throws CustomExceptionMessage
     */
    private static List<Person> CreatePersonArrayList() throws CustomExceptionMessage {
        List<Person> personList = new ArrayList<Person>();
        /**
         * Переменная rndAge для заполнения возраста случайными значениями от 0 до 100
         */
        Random rndAge = new Random();
        /**
         * Переменная personAge - заполнение возраста случайными целочисленными значениями от 0 до 100
         * Переменная personName - заполнение имени случайными строковыми значениями от 5 до 10 символов,
         * первая буква заглавная
         */
        int personAge;
        String personName;

        for (int i = 0; i < 11000; i++) {
            personAge = rndAge.nextInt(101);
            personName = RandomStringUtils.randomAlphabetic(5, 10).toLowerCase();
            personName = personName.substring(0, 1).toUpperCase() + personName.substring(1).toLowerCase();
            /**
             * Выбор пола в зависимости от случайного округленного значения между 0.0 и 1.0
             */
            if (Math.round(Math.random()) == 0) personList.add(new Person(personName, personAge, Sex.MAN));
            else personList.add(new Person(personName, personAge, Sex.WOMAN));
        }
        /**
         * Предварительная сортировка списка для проверки на совпадение имени и возраста
         */
        Comparator<Person> comparator = Comparator.comparing(Person::getAge).thenComparing(Person::getName);
        Collections.sort(personList, comparator);
        /**
         * Перебор списка personList и создание нового исключения CustomExceptionMessage при совпадении имени и возраста
         */
        for (int i = 1; i < personList.size(); i++) {
            if (personList.get(i).getName().equals(personList.get(i - 1).getName())
                    && personList.get(i).getAge() == personList.get(i - 1).getAge()) {
                throw new CustomExceptionMessage("Имя и возраст совпадают!");
            }
        }
        return personList;
    }

}
