package org.pedrojaraujo.dto;

import org.pedrojaraujo.Task;

public class TaskResponseDTO {
    public Long id;
    public String title;
    public String description;
    public Boolean completed;
    public Integer priority;
    public Long user_id;
    private Long category_id;

    public static TaskResponseDTO fromEntity(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.id = task.getUser().getTasks().getFirst().id;
        dto.title = task.getTitle();
        dto.description = task.getDescription();
        dto.completed = task.isCompleted();
        dto.priority = task.getPriority();
        dto.category_id = task.getCategory().id;
        dto.user_id= task.getUser().id;
        return dto;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}
