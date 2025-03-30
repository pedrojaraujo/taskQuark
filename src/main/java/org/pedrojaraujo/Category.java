package org.pedrojaraujo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;


@Entity
public class Category extends PanacheEntity {

    private String name;


    //Relacionamento
    @OneToMany(mappedBy = "category")
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
