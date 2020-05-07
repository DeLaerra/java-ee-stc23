package lesson05.task01;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
        Sex sex;
        Random random = new Random();
        AnimalCatalogService animalCatalogService = new AnimalCatalogService();

//      Добавление в ANIMALS_LIST (основной список) животных со случайными значениями атрибутов
        for (int i = 0; i < 10000; i++) {
            name = RandomStringUtils.randomAlphabetic(4, 10).toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            age = random.nextInt(50) + 20;
            if (Math.round(Math.random()) == 0) sex = Sex.MAN;
            else sex = Sex.WOMAN;
            owner = new Person(name, age, sex);

            nickname = RandomStringUtils.randomAlphabetic(2, 4).toLowerCase();
            nickname = nickname.substring(0, 1).toUpperCase() + nickname.substring(1);
            weight = (random.nextDouble() + 0.1) * 10.0;

            animal = new Animal(nickname, owner, weight);
            Animal.getAnimalsList().add(animal);

/*          Добавление в ANIMALS_MAP для быстрого поиска животных по ключу-кличке (nickname),
            в качестве значения передается список животных с одинаковой кличкой */

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
//      Сортировка списка ANIMALS_LIST
        sortAnimals();
    }

    /**
     * Метод для добавления новых животных в основной список ANIMALS_LIST и доп. коллекцию ANIMALS_MAP
     *
     * @param newAnimal - новое животное
     * @throws RuntimeException
     */
    public void addNewAnimal(Animal newAnimal) throws RuntimeException {
        if (Animal.getAnimalsList().contains(newAnimal) &&
                Animal.getAnimalsMap().entrySet()
                        .stream()
                        .anyMatch(value -> value.getValue().contains(newAnimal)))
            throw new RuntimeException("Данное животное уже добавлено!");
        else {
            if (!Animal.getAnimalsList().contains(newAnimal)) {
                Animal.getAnimalsList().add(newAnimal);
            }

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
     * @param uuid        - UUID, по которому происходит поиск нужного животного в списке ANIMALS_LIST
     * @param newNickname - новая кличка
     * @param weight      - новый вес
     * @param owner       - новый владелец
     */
    public void changeAnimalData(String uuid, String newNickname, double weight, Person owner) {
        String oldNickname = Animal.getAnimalsList()
                .stream()
                .filter(animal -> animal.getUuid().equals(uuid))
                .findFirst().get().getNickname();
        Animal changedAnimal = Animal.getAnimalsList()
                .stream()
                .filter(animal -> animal.getUuid().equals(uuid))
                .findFirst().get();

        if (Animal.getAnimalsList()
                .stream()
                .anyMatch(animal -> animal.getUuid().equals(uuid))) {
            Animal.getAnimalsList()
                    .stream()
                    .filter(animal -> animal.getUuid().equals(uuid))
                    .forEach(animal -> {
                        animal.setNickname(newNickname);
                        animal.setWeight(weight);
                        animal.setOwner(owner);
                    });
            System.out.println();
            System.out.println("Данные изменены.");
            Animal.getAnimalsList()
                    .stream()
                    .filter(animal -> animal.getUuid().equals(uuid))
                    .forEach(System.out::println);

//          Удаляем животное с новой кличкой из того значения ANIMALS_MAP, где ключом является старая кличка
            List<Animal> oldAnimalList = Animal.getAnimalsMap().get(oldNickname);
            if (oldAnimalList.size() > 1) {
                oldAnimalList.removeIf(animal -> animal.getUuid().equals(uuid));
                Animal.getAnimalsMap().put(oldNickname, oldAnimalList);
            } else {
                Animal.getAnimalsMap().entrySet().removeIf(entry -> entry.getKey().equals(oldNickname));
            }

            addNewAnimal(changedAnimal);

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
