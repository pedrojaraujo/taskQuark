package org.pedrojaraujo.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
    public void createUser(TaskUser user) {
        userRepository.persist(user);
    }
}
