package lesson05.task01;

/**
 * Класс с точкой входа
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */

public class Main {

    public static void main(String[] args) {
        AnimalCatalogService animalCatalogService = new AnimalCatalogService();

        animalCatalogService.createAnimals();
        Animal.getAnimalsList().forEach(System.out::println);
        System.out.println();

        try {
            animalCatalogService.addNewAnimal(new Animal("Ra",
                    new Person("John Doe", 30, Sex.MAN), 3.86));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        animalCatalogService.findAnimalInMap("Ra");

        String uuid = Animal.getAnimalsList().get(Animal.getAnimalsList().size() - 1).getUuid();
        animalCatalogService.changeAnimalData(uuid, "Kitty", 3.90,
                new Person("Jane Doe", 28, Sex.WOMAN));
    }
}
