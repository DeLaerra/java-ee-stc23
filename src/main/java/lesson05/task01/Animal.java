package lesson05.task01;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Класс для хранения данных о животных
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Animal {
    /**
     * В ANIMALS_LIST хранятся данные о животных.
     * ANIMALS_MAP - дополнительная коллекция для эффективного поиска по кличке
     */
    private static final List<Animal> ANIMALS_LIST = new ArrayList<>();
    private static final Map<String, List<Animal>> ANIMALS_MAP = new HashMap<>();
    private String uuid;
    private String nickname;
    private Person owner;
    private double weight;


    public Animal(String nickname, Person owner, double weight) {
        this.uuid = UUID.randomUUID().toString();
        this.nickname = nickname;
        this.owner = owner;
/**
 * Округление полученного значения веса weight до 2 знаков после запятой
 */
        BigDecimal bd = new BigDecimal(Double.toString(weight));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        this.weight = bd.doubleValue();
    }

    public static List<Animal> getAnimalsList() {
        return ANIMALS_LIST;
    }

    public static Map<String, List<Animal>> getAnimalsMap() {
        return ANIMALS_MAP;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "UUID: " + uuid + ", кличка: " + nickname + ", вес: " + weight + "." + System.lineSeparator() + owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Double.compare(animal.weight, weight) == 0 &&
                uuid.equals(animal.uuid) &&
                nickname.equals(animal.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nickname, weight);
    }
}
