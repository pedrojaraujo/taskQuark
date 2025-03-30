package org.pedrojaraujo.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.pedrojaraujo.Category;
import org.pedrojaraujo.repositories.CategoryRepository;
import java.util.List;


@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    @Inject
    CategoryRepository categoryRepository;

    @GET
    public List<Category> getCategories() {
        return categoryRepository.listAll();
    }

    @POST
    @Transactional
    public void create(Category category) {
        categoryRepository.persist(category);
    }
}
