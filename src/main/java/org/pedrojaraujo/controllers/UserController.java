package org.pedrojaraujo.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pedrojaraujo.TaskUser;
import org.pedrojaraujo.dto.UserRequestDTO;
import org.pedrojaraujo.dto.UserResponseDTO;
import org.pedrojaraujo.repositories.UserRepository;
import org.pedrojaraujo.utils.DeleteUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "Gerenciamento do usuário")
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    @Operation(summary = "Lista todos os usuários.", description = "Mostra a lista de todos os usuários.")
    public List<UserResponseDTO> getUsers() {
        try {
            return userRepository.listAll().stream()
                    .map(UserResponseDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Lista usuário por ID.", description = "Mostra o usuário correspondente ao ID fornecido.")
     public Response getUsersById(@PathParam("id") long id) {

        try {
            TaskUser user = userRepository.findById(id);

            if ( user == null ) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Tarefa com ID " + id + " não encontrada.").build();
            }
            UserResponseDTO dto = UserResponseDTO.fromEntity(user);
            return Response.ok(dto).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar a tarefa: " + e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    @Operation(summary = "Cria usuários.", description = "Cria um ou mais usuários através de uma lista.")
    public Response createUser(@Valid List<UserRequestDTO> userDTOs) {

        if (userDTOs == null || userDTOs.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A lista de usuários está vazia.").build();
        }

        try {
            List<TaskUser> users = new ArrayList<>();

            for (UserRequestDTO dto : userDTOs) {
                TaskUser user = new TaskUser();
                user.setName(dto.name);
                user.setEmail(dto.email);
                userRepository.persistAndFlush(user);
                users.add(user);
            }

            return Response.status(Response.Status.CREATED).entity(users).build();

        } catch (PersistenceException e) {
            if (e.getCause() != null && e.getCause().getMessage().contains("Unique index or primary key violation")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Já existe um usuário com este e-mail.").build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar usuário: " + e.getMessage()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage()).build();
        }
    }



    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza usuário por ID.", description = "Atualiza o usuário correspondente ao ID fornecido.")
    public Response updateUser(@PathParam("id") Long id, @Valid UserRequestDTO updateDTO) {
        try {
            TaskUser user = userRepository.findById(id);

            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            user.setName(updateDTO.name);
            user.setEmail(updateDTO.email);

            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar o usuário: " + e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Deleta usuário por ID.", description = "Deleta o usuário correspondente ao ID fornecido.")
    public Response deleteUser(@PathParam("id") Long id) {
        return DeleteUtil.deleteEntity(()-> userRepository.deleteById(id), "Usuário");
    }


}
