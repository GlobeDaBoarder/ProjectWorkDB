package base.annotation.testClient;

import base.annotation.Id;
import base.annotation.Property;
import base.annotation.GlobeDbEntity;

import java.util.UUID;

@GlobeDbEntity
public class User {
    @Id
    private UUID id;
    @Property
    private String username;
    @Property
    private String password;
    private int age;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private void initMethodToTest(){
        System.out.println("init!");
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
