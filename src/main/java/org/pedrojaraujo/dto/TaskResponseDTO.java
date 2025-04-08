package org.pedrojaraujo.dto;

import org.pedrojaraujo.Task;

public class TaskResponseDTO {
    public Long id;
    public String title;
    public String description;
    public Boolean completed;
    public Integer priority;
    public String categoryName;
    public Long categoryId;
    public String userName;
    public Long userId;

    public static TaskResponseDTO fromEntity(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.id = task.getUser().getTasks().getFirst().id;
        dto.title = task.getTitle();
        dto.description = task.getDescription();
        dto.completed = task.isCompleted();
        dto.priority = task.getPriority();
        dto.categoryId = task.getCategory().id;
        dto.userId = task.getUser().id;
        return dto;
    }
}
