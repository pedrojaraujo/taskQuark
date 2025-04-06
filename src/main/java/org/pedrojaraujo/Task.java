package org.pedrojaraujo;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;


@Entity
public class Task extends PanacheEntity {

    @Column(nullable = false)
    @NotBlank(message = "É necessário um título")
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean completed;


    private int priority; // 1 (baixa), 2(média), 3(alta)

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private TaskUser user;

    //Getters e Setters


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TaskUser getUser() {
        return user;
    }

    public void setUser(TaskUser user) {
        this.user = user;
    }

}
