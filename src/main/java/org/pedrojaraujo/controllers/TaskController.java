package org.pedrojaraujo.controllers;


import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pedrojaraujo.Category;
import org.pedrojaraujo.Task;
import org.pedrojaraujo.TaskUser;
import org.pedrojaraujo.dto.TaskRequestDTO;
import org.pedrojaraujo.repositories.CategoryRepository;
import org.pedrojaraujo.repositories.TaskRepository;
import org.pedrojaraujo.repositories.UserRepository;
import org.pedrojaraujo.utils.DeleteUtil;

import java.util.ArrayList;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tasks", description = "Gerenciamento de tarefas")
public class TaskController {

    @Inject
    TaskRepository taskRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    UserRepository userRepository;

    @GET
    public List<Task> getTasks() {
        try {
            return taskRepository.listAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/{id}")
    public Task getTaskById(@PathParam("id") Long id) {
        try {
            return taskRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateTask(@PathParam("id") Long id, @Valid TaskRequestDTO dto) {
        try {
            Task task = taskRepository.findById(id);
            if (task == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Tarefa com ID " + id + " não encontrada.").build();
            }

            task.setTitle(dto.title);
            task.setDescription(dto.description);
            task.setCompleted(dto.completed);
            task.setPriority(dto.priority);

            Category category = categoryRepository.findById(dto.category_id);
            if (category == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Categoria com ID " + dto.category_id + " não encontrada.").build();
            }

            TaskUser user = userRepository.findById(dto.user_id);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Usuário com ID " + dto.user_id + " não encontrado.").build();
            }

            task.setCategory(category);
            task.setUser(user);

            return Response.ok(task).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar a tarefa: " + e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    public Response createTask(@Valid List<TaskRequestDTO> taskDTOs) {
        try {
            if (taskDTOs.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("A lista de tarefas está vazia.").build();
            }

            List<Task> tasks = new ArrayList<>();

            for (TaskRequestDTO dto : taskDTOs) {
                Task task = new Task();
                task.setTitle(dto.title);
                task.setDescription(dto.description);
                task.setCompleted(dto.completed);
                task.setPriority(dto.priority);

                // Buscar entidades existentes pelo ID
                Category category = categoryRepository.findById(dto.category_id);
                if (category == null) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Categoria com ID " + dto.category_id + " não encontrada.").build();
                }

                TaskUser user = userRepository.findById(dto.user_id);
                if (user == null) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Usuário com ID " + dto.user_id + " não encontrado.").build();
                }

                task.setCategory(category);
                task.setUser(user);

                taskRepository.persistAndFlush(task);
                tasks.add(task);
            }

            return Response.status(Response.Status.CREATED).entity(tasks).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar as tarefas: " + e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        return DeleteUtil.deleteEntity(()-> taskRepository.deleteById(id), "Tarefa");
    }

}
