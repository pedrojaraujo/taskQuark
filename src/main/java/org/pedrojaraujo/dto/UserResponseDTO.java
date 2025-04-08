package org.pedrojaraujo.dto;

import org.pedrojaraujo.Task;
import org.pedrojaraujo.TaskUser;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponseDTO {
    public Long id;
    public String name;
    public String email;
    public List<TaskSummaryDTO> tasks;

    public static UserResponseDTO fromEntity(TaskUser user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.id = user.id;
        dto.name = user.getName();
        dto.email = user.getEmail();

        if (user.getTasks() != null) {
            dto.tasks = user.getTasks().stream()
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