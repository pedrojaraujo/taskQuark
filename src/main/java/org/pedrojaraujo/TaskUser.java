package org.pedrojaraujo;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.OneToMany;


@Entity
public class TaskUser extends PanacheEntity {

    private String name;
    private String email;

    //Relacionamento
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    //Getters e Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
