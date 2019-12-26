package kz.ais.eshop.payloads;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotNull(message = "first name is required")
    private String firstName;
    @NotNull(message = "last name is required")
    private String lastName;
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "username is required")
    private String username;
    @NotNull(message = "password is required")
    @Size(min = 6, max = 20)
    private String password;
    @NotNull(message = "phone number is required")
    @Column(length = 11)
    private String phoneNumber;

}
