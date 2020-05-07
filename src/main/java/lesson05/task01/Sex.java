package lesson05.task01;

public enum Sex {
    MAN("мужской"),
    WOMAN("женский");

    private final String sex;

    public String getSex() {
        return sex;
    }

    Sex(final String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return sex;
    }
}
