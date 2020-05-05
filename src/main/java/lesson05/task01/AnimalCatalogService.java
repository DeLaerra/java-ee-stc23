package lesson05.task01;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * Класс, содержащий методы для управления картотекой животных
 *
 * @author Marina_Larionova
 * @version 1.0.0
 * @see Animal
 * @see Person
 */
public class AnimalCatalogService {
    /**
     * Метод для первичного наполнения картотеки животными
     */
    public void createAnimals() {
        Animal animal;
        String nickname;
        double weight;
        Person owner;
        String name;
        int age;
        String sex;
        Random random = new Random();
        AnimalCatalogService animalCatalogService = new AnimalCatalogService();
        /**
         * Добавление в ANIMALS_LIST (основной список) животных со случайными значениями атрибутов
         */
        for (int i = 0; i < 10000; i++) {
            name = RandomStringUtils.randomAlphabetic(4, 10).toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            age = random.nextInt(50) + 20;
            if (Math.round(Math.random()) == 0) sex = "мужской";
            else sex = "женский";
            owner = new Person(name, age, sex);

            nickname = RandomStringUtils.randomAlphabetic(2, 4).toLowerCase();
            nickname = nickname.substring(0, 1).toUpperCase() + nickname.substring(1);
            weight = (random.nextDouble() + 0.1) * 10.0;

            animal = new Animal(nickname, owner, weight);
            Animal.getAnimalsList().add(animal);
            /**
             * Добавление в ANIMALS_MAP для быстрого поиска животных по ключу-кличке (nickname),
             * в качестве значения передается список животных с одинаковой кличкой
             */
            if (Animal.getAnimalsMap().containsKey(nickname)) {
                List<Animal> tempList = Animal.getAnimalsMap().get(nickname);
                tempList.add(animal);
                Animal.getAnimalsMap().put(nickname, tempList);
            } else {
                List<Animal> animalList = new ArrayList<>();
                animalList.add(animal);
                Animal.getAnimalsMap().put(nickname, animalList);
            }
        }
        /**
         * Сортировка списка ANIMALS_LIST
         */
        sortAnimals();
    }

    /**
     * Метод для добавления новых животных в основной список ANIMALS_LIST и доп. коллекцию ANIMALS_MAP
     *
     * @param newAnimal - новое животное
     * @throws RuntimeException
     */
    public void addNewAnimal(Animal newAnimal) throws RuntimeException {
        if (Animal.getAnimalsList().contains(newAnimal)) throw new RuntimeException("Данное животное уже добавлено!");
        else {
            Animal.getAnimalsList().add(newAnimal);

            if (Animal.getAnimalsMap().containsKey(newAnimal.getNickname())) {
                List<Animal> tempList = Animal.getAnimalsMap().get(newAnimal.getNickname());
                tempList.add(newAnimal);
                Animal.getAnimalsMap().put(newAnimal.getNickname(), tempList);
            } else {
                List<Animal> animalList = new ArrayList<>();
                animalList.add(newAnimal);
                Animal.getAnimalsMap().put(newAnimal.getNickname(), animalList);
            }
        }
        sortAnimals();
    }

    /**
     * Метод для быстрого поиска животных с кличкой nickname в коллекции ANIMALS_MAP
     *
     * @param nickname - кличка для поиска
     */
    public void findAnimalInMap(String nickname) {
        List<Animal> animalsForSearchList = Animal.getAnimalsMap().get(nickname);
        try {
            System.out.println("Животные с кличкой " + nickname + ":");
            animalsForSearchList.forEach(System.out::println);
        } catch (NullPointerException e) {
            System.out.println("Животного с кличкой " + nickname + " нет в базе");
        }
    }

    /**
     * Метод для изменения данных животного по UUID
     *
     * @param uuid     - UUID, по которому происходит поиск нужного животного в списке ANIMALS_LIST
     * @param nickname - новая кличка
     * @param weight   - новый вес
     * @param owner    - новый владелец
     */
    public void changeAnimalData(String uuid, String nickname, double weight, Person owner) {
        if (Animal.getAnimalsList()
                .stream()
                .anyMatch(animal -> animal.getUuid().equals(uuid))) {
            Animal.getAnimalsList()
                    .stream()
                    .filter(animal -> animal.getUuid().equals(uuid))
                    .forEach(animal -> {
                        animal.setNickname(nickname);
                        animal.setWeight(weight);
                        animal.setOwner(owner);
                    });
            System.out.println();
            System.out.println("Данные изменены.");
            Animal.getAnimalsList()
                    .stream()
                    .filter(animal -> animal.getUuid().equals(uuid))
                    .forEach(System.out::println);
        } else System.out.println("Животного с UUID " + uuid + " нет в базе");
    }

    /**
     * Метод для сортировки списка ANIMALS_LIST по имени владельца, кличке и весу
     */
    public void sortAnimals() {
        Comparator<Animal> comparator = Comparator
                .comparing(Animal::getOwner, Comparator.comparing(Person::getName))
                .thenComparing(Animal::getNickname)
                .thenComparing(Animal::getWeight);
        Animal.getAnimalsList().sort(comparator);
    }
}
