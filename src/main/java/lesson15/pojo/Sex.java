package lesson15.pojo;

public enum Sex {
    MALE("male"),
    FEMALE("female");

    private final String sex;

    Sex(final String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return sex;
    }
}
