package lesson03.task03;

import java.util.*;
/**
 * Класс для работы с объектами класса Number и его наследников
 *
 * @param <T> extends Number
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class MathBox<T extends Number> extends ObjectBox<Number> {
    /**
     * Конструктор, добавляющий объекты класса Number в коллекцию objectSet
     * @param numbers - массив объектов класса Number и его наследников
     * @throws RuntimeException
     */
    public MathBox(T[] numbers) {
        for (Number number : numbers) {
            objectSet.add(number.doubleValue());
        }
        if (numbers.length == 0) throw new RuntimeException("Длина массива равна нулю!");
    }
    /**
     * Метод, суммирующий значение всех объектов массива. Т.к. объекты могут быть класса Number и наследников Number,
     * выходное значение приводится к типу double
     * @return summ
     */
    double summator() {
        double summ = 0.0;
        for (Object object : objectSet) {
            summ = summ + (double) object;
        }
        return summ;
    }
    /**
     * Метод, выполняющий деление всех элементов коллекции objectSet на делитель split.
     * @param split - делитель
     */
    void splitter(int split) {
        Set<Number> tempSet = new HashSet<>(objectSet.size());

        Iterator<Object> it = objectSet.iterator();
        while (it.hasNext()) {
            tempSet.add((double) it.next() / split);
            it.remove();
        }

        objectSet.addAll(tempSet);
    }

    /**
     * Метод, убирающий из колекции objectSet число del
     * @param del - лишнее число
     */
    void deleteInteger(int del) {
        Number number = (double) del;
        objectSet.removeIf(n -> n.equals(number));
    }

    public static void main(String[] args) {
        int del = 1;
        int split = 2;
        Object object = del;
        Number[] numbers = new Number[10];

        for (int i = 0; i < 10; i++) {
            numbers[i] = i;
        }

        MathBox<Number> mathBox = new MathBox<>(numbers);

        System.out.println("Изначальная коллекция:");
        mathBox.dump();
        System.out.println();

        System.out.println("Сумма всех компонентов коллекции: " + mathBox.summator());

        mathBox.splitter(split);
        System.out.println("После деления на " + split + ":");
        System.out.println(mathBox.objectSet);

        mathBox.deleteInteger(del);
        System.out.println("После удаления числа " + del + ":");
        System.out.println(mathBox.objectSet);
/**
 * Методы класса ObjectBox.
 * Метод addObject не работет для Object object, но работает для int del
 */
//        mathBox.addObject(object);
        mathBox.addObject(del);
        mathBox.deleteObject(del);
        mathBox.dump();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox mathBox = (MathBox) o;
        return objectSet.equals(mathBox.objectSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectSet);
    }

    @Override
    public String toString() {
        return "MathBox{" +
                "numbersHashSet=" + objectSet +
                '}';
    }
}

