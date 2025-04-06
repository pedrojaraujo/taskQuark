package org.pedrojaraujo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequestDTO {
    @NotBlank(message = "O título é obrigatório.")
    public String title;

    public String description;

    @NotNull(message = "O status de conclusão é obrigatório.")
    public Boolean completed;

    @NotNull(message = "A prioridade é obrigatória.")
    public Integer priority;

    @NotNull(message = "O ID da categoria é obrigatório.")
    public Long category_id;

    @NotNull(message = "O ID do usuário é obrigatório.")
    public Long user_id;
}
