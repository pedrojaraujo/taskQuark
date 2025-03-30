package org.pedrojaraujo.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pedrojaraujo.TaskUser;
import org.pedrojaraujo.repositories.UserRepository;
import java.util.List;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    public List<TaskUser> getUsers() {
        return userRepository.listAll();
    }

    @POST
    @Transactional
    public Response createUser(TaskUser user) {

        if (user == null || user.getName() == null || user.getName().trim().isEmpty()
                || user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro: Solicitamos apenas o nome de usuário e o e-mail, ambos obrigatórios.")
                    .build();
        }

        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}
