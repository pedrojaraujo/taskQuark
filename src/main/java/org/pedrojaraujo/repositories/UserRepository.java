package org.pedrojaraujo.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.pedrojaraujo.TaskUser;

@ApplicationScoped
public class UserRepository implements PanacheRepository<TaskUser> {
}
