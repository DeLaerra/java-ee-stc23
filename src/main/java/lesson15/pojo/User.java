package lesson15.pojo;

import java.util.UUID;
/**
 * Класс для создания пользователей
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class User {
    private String uuid;
    private String password;
    private String nickname;
    private String name;
    private int age;
    private Sex sex;
    private String email;
    private boolean isModerator;

    public User(String password, String nickname, String name, int age, Sex sex, String email) {
        this.uuid = UUID.randomUUID().toString();
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.isModerator = false;
    }

    public String getUuid() {
        return uuid;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public boolean isModerator() {
        return isModerator;
    }


    @Override
    public String toString() {
        return "nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", isModerator=" + isModerator;
    }
}
