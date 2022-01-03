package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty(value="first_name")
    private String firstName;

    @JsonProperty(value="last_name")
    private String lastName;

    private String id;
    private String email;
    private String avatar;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

}
