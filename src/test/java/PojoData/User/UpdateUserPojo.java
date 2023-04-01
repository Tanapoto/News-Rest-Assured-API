package PojoData.User;

import lombok.Data;

@Data
public class UpdateUserPojo {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    public UpdateUserPojo(String firstName, String lastName, String email, String password, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
