package org.pedrojaraujo.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import org.pedrojaraujo.Category;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {
}
