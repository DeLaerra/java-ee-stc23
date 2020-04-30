package lesson03.task01;

import java.util.*;

public class MathBox {
    private Set<Number> numbersHashSet = new HashSet<>();

    public Set<Number> getNumbersHashSet() {
        return numbersHashSet;
    }

    public void setNumbersHashSet(Set<Number> numbersHashSet) {
        this.numbersHashSet = numbersHashSet;
    }


    public MathBox(Number[] numbers) {
        if (numbers.length == 0) throw new RuntimeException("Длина массива равна нулю!");
        numbersHashSet.addAll(Arrays.asList(numbers));
    }


    double summator(Set<Number> set) {
        double summ = 0.0;
        for (Number number : set) {
            summ = summ + number.doubleValue();
        }
        return summ;
    }

    void splitter(Set<Number> set, int split) {
        Set<Number> tempSet = new HashSet<>(set.size());

        Iterator<? extends Number> it = set.iterator();
        while (it.hasNext()) {
            tempSet.add(it.next().doubleValue() / split);
            it.remove();
        }

        set.addAll(tempSet);
    }

    void deleteInteger(Set<Number> set, int del) {
        Number number = (double)del;
        set.removeIf(n -> n.equals(number));
    }

    public static void main(String[] args) {
        int del = 1;
        int split = 2;
        Number[] numbers = new Number[10];
        for (int i = 0; i < 10; i++) {
            numbers[i] = i;
        }

        MathBox mathBox = new MathBox(numbers);
        System.out.println("Изначальная коллекция:");
        System.out.println(mathBox.numbersHashSet);

        System.out.println("Сумма всех компонентов коллекции: " + mathBox.summator(mathBox.numbersHashSet));

        mathBox.splitter(mathBox.numbersHashSet, split);
        System.out.println("После деления на " + split + ":");
        System.out.println(mathBox.numbersHashSet);

        mathBox.deleteInteger(mathBox.numbersHashSet, del);
        System.out.println("После удаления числа " + del + ":");
        System.out.println(mathBox.numbersHashSet);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox mathBox = (MathBox) o;
        return numbersHashSet.equals(mathBox.numbersHashSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbersHashSet);
    }

    @Override
    public String toString() {
        return "MathBox{" +
                "numbersHashSet=" + numbersHashSet +
                '}';
    }
}

