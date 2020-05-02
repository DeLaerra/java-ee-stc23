package lesson03.task01;

import java.util.*;

/**
 * Класс для работы с объектами класса Number и его наследников
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class MathBox {
    private final Set<Number> numbersHashSet = new HashSet<>();

    public Set<Number> getNumbersHashSet() {
        return numbersHashSet;
    }

    /**
     * Конструктор, добавляющий объекты класса Number в коллекцию numbersHashSet
     *
     * @param numbers - массив объектов класса Number
     * @throws RuntimeException
     */
    public MathBox(Number[] numbers) {
        if (numbers.length == 0) throw new RuntimeException("Длина массива равна нулю!");
        numbersHashSet.addAll(Arrays.asList(numbers));
    }

    /**
     * Метод, суммирующий значение всех объектов массива. Т.к. объекты могут быть класса Number и наследников Number,
     * выходное значение приводится к типу double
     *
     * @param set массив объектов класса Number
     * @return summ
     */
    double summator(Set<Number> set) {
        double summ = 0.0;
        for (Number number : set) {
            summ = summ + number.doubleValue();
        }
        return summ;
    }

    /**
     * Метод, выполняющий деление всех элементов коллекции objectSet на делитель split.
     *
     * @param set   - массив объектов класса Number
     * @param split - делитель
     */
    void splitter(Set<Number> set, int split) {
        Set<Number> tempSet = new HashSet<>(set.size());

        Iterator<? extends Number> it = set.iterator();
        while (it.hasNext()) {
            tempSet.add(it.next().doubleValue() / split);
            it.remove();
        }

        set.addAll(tempSet);
    }

    /**
     * Метод, убирающий из колекции objectSet число del
     *
     * @param set - массив объектов класса Number
     * @param del - лишнее число
     */
    void deleteInteger(Set<Number> set, int del) {
        Number number = (double) del;
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

