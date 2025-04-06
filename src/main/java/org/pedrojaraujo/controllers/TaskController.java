package org.pedrojaraujo.controllers;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pedrojaraujo.Task;
import org.pedrojaraujo.repositories.TaskRepository;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tasks", description = "Gerenciamento de tarefas")
public class TaskController {

    @Inject
    TaskRepository taskRepository;

    @GET
    public List<Task> getTasks() {
        return taskRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Task getTaskById(@PathParam("id") Long id) {
        return taskRepository.findById(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateTask(@Valid @PathParam("id") Long id, Task updatedTask) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());
        task.setCategory(updatedTask.getCategory());
        task.setUser(updatedTask.getUser());

        return Response.ok(task).build();
    }

    @POST
    @Transactional
    public Response createTask(@Valid Task task) {
        taskRepository.persist(task);
        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        boolean deleted = taskRepository.deleteById(id);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
