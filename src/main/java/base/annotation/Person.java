package base.annotation;

@JsonSerializable
public class Person {

    @JsonField
    private String firstName;

    @JsonField
    private String lastName;

    @JsonField(key = "personAge")
    private String age;

    private String address;

    public Person(String firstName, String lastName, String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = null;
    }

    @InitMethod
    private void initNames() {
        this.firstName = this.firstName.substring(0, 1).toUpperCase()
                + this.firstName.substring(1);
        this.lastName = this.lastName.substring(0, 1).toUpperCase()
                + this.lastName.substring(1);
    }

    // Standard getters and setters
}
