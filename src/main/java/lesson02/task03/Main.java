package lesson02.task03;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Person> personList = CreatePersonArrayList();
        FirstSortingAlgorithm firstSortingAlgorithm = new FirstSortingAlgorithm();
        SecondSortingAlgorithm secondSortingAlgorithm = new SecondSortingAlgorithm();

        long beforeFirstSorting = System.nanoTime();
        firstSortingAlgorithm.SortPersonArrayList(personList);
        long afterFirstSorting = System.nanoTime() - beforeFirstSorting;

        long beforeSecondSorting = System.nanoTime();
        secondSortingAlgorithm.SortPersonArrayList(personList);
        long afterSecondSorting = System.nanoTime() - beforeSecondSorting;

        for (Person person : personList) {
            System.out.println(person);
        }

        System.out.println();
        System.out.println("Время работы первого алгоритма сортировки: " + afterFirstSorting + " ns");
        System.out.println("Время работы второго алгоритма сортировки: " + afterSecondSorting + " ns");
    }

    private static List<Person> CreatePersonArrayList() {
        List<Person> personList = new ArrayList<Person>();
        Random rndAge = new Random();
        int personAge;
        String personName;

        for (int i = 0; i < 11000; i++) {
            personAge = rndAge.nextInt(101);
            personName = RandomStringUtils.randomAlphabetic(5, 10);
            if (Math.round(Math.random()) == 0) personList.add(new Person(personName, personAge, Sex.MAN));
            else personList.add(new Person(personName, personAge, Sex.WOMAN));
        }

        Comparator<Person> comparator = Comparator.comparing(Person::getAge).thenComparing(Person::getName);
        Collections.sort(personList, comparator);

        for (int i = 1; i < personList.size(); i++) {
            try {
                if (personList.get(i).getName().equals(personList.get(i - 1).getName())
                        && personList.get(i).getAge() == personList.get(i - 1).getAge()) {
                    throw new CustomExceptionMessage("Имя и возраст совпадают!");
                }
            } catch (CustomExceptionMessage e) {
                e.printStackTrace();
            }
        }

        return personList;
    }

}
