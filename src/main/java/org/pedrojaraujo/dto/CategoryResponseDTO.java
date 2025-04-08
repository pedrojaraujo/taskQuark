package org.pedrojaraujo.dto;

import org.pedrojaraujo.Category;
import org.pedrojaraujo.Task;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryResponseDTO {
    public Long id;
    public String name;
    public List<TaskSummaryDTO> tasks;

    public static CategoryResponseDTO fromEntity(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.id = category.id;
        dto.name = category.getName();

        if (category.getTasks() != null) {
            dto.tasks = category.getTasks().stream()
                    .map(TaskSummaryDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        return dto;
    }

    // Classe interna para resumir as informações de Task
    public static class TaskSummaryDTO {
        public Long id;
        public String title;
        public Boolean completed;

        public static TaskSummaryDTO fromEntity(Task task) {
            TaskSummaryDTO dto = new TaskSummaryDTO();
            dto.id = task.id;
            dto.title = task.getTitle();
            dto.completed = task.isCompleted();
            return dto;
        }
    }
}

