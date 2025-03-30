package org.pedrojaraujo.controllers;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pedrojaraujo.Task;
import org.pedrojaraujo.repositories.TaskRepository;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskController {

    @Inject
    TaskRepository taskRepository;

    @GET
    public List<Task> getTasks() {
        return taskRepository.listAll();
    }

    @POST
    @Transactional
    public Response createTask(Task task) {
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
