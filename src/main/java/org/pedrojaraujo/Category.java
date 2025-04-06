package org.pedrojaraujo;


import java.util.List;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.validation.constraints.NotBlank;
;


@Entity
public class Category extends PanacheEntity {

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O nome da categoria é necessário.")
    private String name;


    //Relacionamento
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    // Getters e Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
