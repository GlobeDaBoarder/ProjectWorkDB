package base.annotation.testClient;

import base.annotation.JsonField;
import base.annotation.JsonSerializable;

@JsonSerializable
public class User {
    @JsonField
    private String username;
    @JsonField
    private String password;
    private int age;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private void initMethodToTest(){
        System.out.println("init!");
    }
}
