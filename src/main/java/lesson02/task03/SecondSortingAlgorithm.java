package lesson02.task03;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SecondSortingAlgorithm implements Sortable, Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
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

    @Override
    public void SortPersonArrayList(List<Person> personArrayList) {
        Collections.sort(personArrayList, new SecondSortingAlgorithm());
    }
}
