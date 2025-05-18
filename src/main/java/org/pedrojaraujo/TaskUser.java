package org.pedrojaraujo;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
public class TaskUser extends PanacheEntity {

    @Column(nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String apiKey;

    // Gerar API Key
    public void generateApiKey() {
        this.apiKey = UUID.randomUUID().toString();
    }

    //Relacionamento
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
