package org.pedrojaraujo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    public String name;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    public String email;

    @NotBlank(message = "A senha é obrigatória.")
    public String password;
}
