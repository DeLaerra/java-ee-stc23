package lesson03.task03;

import java.util.*;

/**
 * Класс для работы с объектами класса Number и его наследников
 *
 * @param <T> extends Number
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class MathBox<T extends Number> extends ObjectBox {
    /**
     * Конструктор, добавляющий объекты класса Number в коллекцию objectSet
     *
     * @param numbers - массив объектов класса Number и его наследников
     * @throws RuntimeException
     */
    public MathBox(T[] numbers) {
        for (T number : numbers) {
            addObject(number);
        }
        if (numbers.length == 0) throw new RuntimeException("Длина массива равна нулю!");
    }

    /**
     * Метод для добавления объектов класса Number в коллекцию objectSet
     *
     * @param obj
     */
    @Override
    public void addObject(Object obj) {
        if (obj instanceof Number) {
            super.addObject(obj);
        } else throw new ClassCastException("Это не Number!");
    }

    /**
     * Метод для удаления объектов класса Number в коллекцию objectSet
     *
     * @param obj
     */
    @Override
    public void deleteObject(Object obj) {
        if (obj instanceof Number) {
            super.deleteObject(obj);
        } else throw new ClassCastException("Это не Number!");
    }

    /**
     * Метод, суммирующий значения всех объектов массива. Т.к. объекты могут быть класса Number и наследников Number,
     * выходное значение приводится к типу double
     *
     * @return summ
     */
    double summator() {
        double summ = 0.0;
        for (Object object : objectSet) {
            summ = summ + ((Number) object).doubleValue();
        }
        return summ;
    }

    /**
     * Метод, выполняющий деление всех элементов коллекции objectSet на делитель split.
     *
     * @param split - делитель
     */
    void splitter(double split) {
        Set<Object> tempSet = new HashSet<>(objectSet.size());

        for (Object obj : objectSet) {
            if (obj instanceof Byte || obj instanceof Short || obj instanceof Integer) {
                tempSet.add(((Number) obj).intValue() / split);
            } else if (obj instanceof Double || obj instanceof Float) {
                tempSet.add(((Number) obj).doubleValue() / split);
            } else if (obj instanceof Long) {
                tempSet.add(((Number) obj).longValue() / split);
            }
        }
        objectSet = tempSet;
    }

    /**
     * Метод, убирающий из колекции objectSet целое число del
     *
     * @param del - лишнее число
     */
    void deleteInteger(int del) {
        objectSet.removeIf(n -> n.equals(del));
    }

    public static void main(String[] args) {
        int delInt = 1;
        Double delDouble = 1.0;
        int split = 2;
        Object object = new Object();
        Number[] numbers = new Number[10];

        for (int i = 0; i < 5; i++) {
            numbers[i] = i;
            numbers[i + 5] = (double) i;
        }

        MathBox<Number> mathBox = new MathBox<>(numbers);
        System.out.println("Изначальная коллекция:");
        mathBox.dump();
        System.out.println();

        mathBox.deleteInteger(delInt);
        System.out.println("После удаления целого числа " + delInt + ":");
        mathBox.dump();
        System.out.println();

//  Методы класса ObjectBox.
//  Метод addObject не работет для Object object, но работает для int delInt
//      mathBox.addObject(object);
        mathBox.deleteObject(delDouble);
        System.out.println("После удаления числа " + delDouble + ":");
        mathBox.dump();
        System.out.println();

        mathBox.addObject(delDouble);
        System.out.println("После добавления числа " + delDouble + ":");
        mathBox.dump();
        System.out.println();

        mathBox.splitter(split);
        System.out.println("После деления на " + split + ":");
        mathBox.dump();
        System.out.println();

        System.out.println("Сумма всех компонентов коллекции: " + mathBox.summator());
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