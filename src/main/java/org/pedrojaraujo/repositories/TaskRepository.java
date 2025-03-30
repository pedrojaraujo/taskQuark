package org.pedrojaraujo.repositories;


import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import org.pedrojaraujo.Task;



@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task>   {
}
