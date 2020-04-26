package lesson02.task03;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FirstSortingAlgorithm implements Sortable {

    public void SortPersonArrayList(List<Person> personArrayList) {
        Comparator<Person> comparator = Comparator.comparing(Person::getSex).reversed().
                thenComparing(Person::getAge).reversed().thenComparing(Person::getName);

        Collections.sort(personArrayList, comparator);




    }


}
