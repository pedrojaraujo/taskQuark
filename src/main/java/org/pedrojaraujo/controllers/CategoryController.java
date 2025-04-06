package org.pedrojaraujo.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pedrojaraujo.Category;
import org.pedrojaraujo.repositories.CategoryRepository;
import java.util.List;


@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Category", description = "Gerenciamento das categorias.")
public class CategoryController {

    @Inject
    CategoryRepository categoryRepository;

    @GET
    public List<Category> getCategories() {
        return categoryRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Category getCategoriesById(@PathParam("id") Long id) {
        return categoryRepository.findById(id);
    }

    @POST
    @Transactional
    public void create(@Valid Category category) {
        categoryRepository.persist(category);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Category updateCategory ) {

        Category category = categoryRepository.findById(id);

        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        category.setName(updateCategory.getName());
        category.setTasks(updateCategory.getTasks());

        return Response.ok(category).build();

    }
}
