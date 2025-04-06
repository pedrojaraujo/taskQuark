package org.pedrojaraujo.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {

    @NotBlank(message = "O nome da categoria é obrigatório.")
    public String name;
}
