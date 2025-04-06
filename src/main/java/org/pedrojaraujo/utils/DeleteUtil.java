package org.pedrojaraujo.utils;

import jakarta.ws.rs.core.Response;
import java.util.function.Supplier;

public class DeleteUtil {

    public static Response deleteEntity(Supplier<Boolean> deleteOperation, String entityName) {
        try {
            boolean deleted = deleteOperation.get();

            if (deleted) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(entityName + " não encontrada.").build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar " + entityName + ": " + e.getMessage()).build();
        }
    }
}
