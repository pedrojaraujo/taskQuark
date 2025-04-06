package org.pedrojaraujo.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pedrojaraujo.TaskUser;
import org.pedrojaraujo.repositories.UserRepository;
import java.util.List;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "Gerenciamento do usuário")
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    public List<TaskUser> getUsers() {
        return userRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public TaskUser getUsersById(@PathParam("id") long id) {

        return userRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createUser(@Valid TaskUser user) {

        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}
