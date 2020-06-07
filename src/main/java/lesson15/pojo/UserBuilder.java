package lesson15.pojo;

public class UserBuilder {
    private String password;
    private String nickname;
    private String name;
    private int age;
    private Sex sex;
    private String email;

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public UserBuilder setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public User createUser() {
        return new User(password, nickname, name, age, sex, email);
    }
}