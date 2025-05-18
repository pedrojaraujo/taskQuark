package org.pedrojaraujo.controllers;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pedrojaraujo.Category;
import org.pedrojaraujo.dto.CategoryRequestDTO;
import org.pedrojaraujo.dto.CategoryResponseDTO;
import org.pedrojaraujo.repositories.CategoryRepository;
import org.pedrojaraujo.utils.DeleteUtil;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Category", description = "Gerenciamento das categorias.")
public class CategoryController {

    @Inject
    CategoryRepository categoryRepository;

    @GET
    @Path("/public")
    @Operation(summary = "Lista todas as categorias.", description = "Mostra a lista de todas as categorias.")
    public List<CategoryResponseDTO> getCategories() {
       try {
           return categoryRepository.listAll().stream()
                   .map(CategoryResponseDTO::fromEntity)
                   .collect(Collectors.toList());
       } catch ( RuntimeException e) {
           throw new RuntimeException(e);
       }
    }

    @GET
    @Path("/public/{id}")
    @RateLimit(value = 5, window = 1, windowUnit = ChronoUnit.MINUTES)
    @Fallback(fallbackMethod = "fallbackParaRateLimit")
    @Operation(summary = "Lista categoria por ID.", description = "Mostra a lista categoria por ID.")
    public Response getCategoriesById(@PathParam("id") Long id) {
        try {
            Category category = categoryRepository.findById(id);
            if (category == null) {
                return  Response.status(Response.Status.NOT_FOUND)
                        .entity("Categoria com ID" + id + " não encotrada.").build();
            }
            CategoryResponseDTO dto = CategoryResponseDTO.fromEntity(category);
            return Response.ok(dto).build();
        } catch (Exception e ) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar a tarefa: " + e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    @Operation(summary = "Cria categorias.", description = "Cria uma ou mais categorias através de uma lista.")
    public Response create(@Valid List<CategoryRequestDTO> categoryDTOs) {

        if (categoryDTOs == null || categoryDTOs.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A lista de categorias está vazia ou não foi enviada.").build();
        }

        try {
            List<Category> categories = new ArrayList<>();

            for (CategoryRequestDTO dto : categoryDTOs) {
                Category category = new Category();
                category.setName(dto.name);
                categoryRepository.persistAndFlush(category);
                categories.add(category);
            }

            return Response.status(Response.Status.CREATED).entity(categories).build();

        } catch (PersistenceException e) {
            if (e.getCause() != null && e.getCause().getMessage().contains("Unique index or primary key violation")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Já existe uma categoria com este nome.").build();
            }

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar as categorias: " + e.getMessage()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage()).build();
        }
    }



    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza a categoria por ID.", description = "Atualiza a categoria pelo ID apontado.")
    public Response update(@PathParam("id") Long id, @Valid CategoryRequestDTO updateDTO) {
        try {
            Category category = categoryRepository.findById(id);

            if (category == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            category.setName(updateDTO.name);

            return Response.ok(category).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar a categoria: " + e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Deleta a categoria por ID.", description = "Deleta a categoria pelo ID apontado.")
    public Response deleteCategory(@PathParam("id") Long id) {

        return DeleteUtil.deleteEntity(()-> categoryRepository.deleteById(id), "Categoria");
    }



    public Response fallbackParaRateLimit(Long id) {
        return Response.status(Response.Status.TOO_MANY_REQUESTS)
                .entity("Limite de requisições excedido. Tente novamente mais tarde.")
                .build();
    }
}
