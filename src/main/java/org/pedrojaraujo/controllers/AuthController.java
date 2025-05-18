package org.pedrojaraujo.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pedrojaraujo.TaskUser;
import org.pedrojaraujo.dto.ApiKeyResponseDTO;
import org.pedrojaraujo.dto.LoginDTO;
import org.pedrojaraujo.repositories.UserRepository;

import java.util.Objects;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Autenticação", description = "Gerenciamento de autenticação")
public class AuthController {

    @Inject
    UserRepository userRepository;

    @POST
    @Path("/login")
    @Transactional
    @Operation(summary = "Realiza login", description = "Autentica o usuário e retorna uma API Key")
    public Response login(LoginDTO loginDTO) {
        // Validação básica de entrada
        if (loginDTO.email == null || loginDTO.password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email e senha são obrigatórios").build();
        }

        // Buscar usuário por email
        TaskUser user = userRepository.find("email", loginDTO.email).firstResult();

        if (user == null || !Objects.equals(user.getPassword(), loginDTO.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciais inválidas").build();
        }

        // Gerar nova API Key
        user.generateApiKey();

        // Persistir usuário com a nova API Key
        userRepository.persist(user);

        // Retornar a API Key
        return Response.ok(new ApiKeyResponseDTO(user.getApiKey())).build();
    }
}