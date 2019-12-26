package kz.ais.eshop.payloads;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull(message = "username or email is required")
    private String usernameOrEmail;

    @NotNull(message = "password is required")
    private String password;

    
}
