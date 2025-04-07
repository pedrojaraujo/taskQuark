package org.pedrojaraujo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.pedrojaraujo.Category;
import org.pedrojaraujo.TaskUser;
import org.pedrojaraujo.validation.ValidPriority;

public class TaskRequestDTO {
    @NotBlank(message = "O título é obrigatório.")
    public String title;

    public String description;

    @NotNull(message = "O status de conclusão é obrigatório.")
    public Boolean completed;

    @NotNull(message = "A prioridade é obrigatória.")
    @ValidPriority
    public Integer priority;

    public Long category_id;

    public Long user_id;
}
