package org.pedrojaraujo;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.USER)
@ApplicationScoped
public class IdempotencyFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String IDEMPOTENCY_HEADER = "Idempotency-Key";

    @Override
    @Transactional
    public void filter(ContainerRequestContext requestContext) {
        // Apenas verificar métodos não idempotentes
        if (!isIdempotentMethod(requestContext.getMethod())) {
            String idempotencyKey = requestContext.getHeaderString(IDEMPOTENCY_HEADER);

            // Se o cabeçalho foi fornecido
            if (idempotencyKey != null && !idempotencyKey.isEmpty()) {
                String path = requestContext.getUriInfo().getPath();

                // Verificar se já existe um registro com esta chave
                IdempotencyRecord existingRecord = IdempotencyRecord.find("key", idempotencyKey).firstResult();

                if (existingRecord != null) {
                    // Retornar a resposta armazenada
                    Response response = Response
                            .status(existingRecord.responseStatus)
                            .entity(existingRecord.responseBody)
                            .header("X-Idempotent-Replayed", "true")
                            .build();

                    requestContext.abortWith(response);
                } else {
                    // Armazenar a chave para uso posterior
                    requestContext.setProperty("idempotencyKey", idempotencyKey);
                    requestContext.setProperty("requestPath", path);
                }
            }
        }
    }

    @Override
    @Transactional
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)  {
        String idempotencyKey = (String) requestContext.getProperty("idempotencyKey");

        // Se temos uma chave de idempotência para armazenar
        if (idempotencyKey != null) {
            String path = (String) requestContext.getProperty("requestPath");
            IdempotencyRecord record = IdempotencyRecord.create(idempotencyKey, path);

            // Armazenar o código de status
            record.responseStatus = responseContext.getStatus();

            // Armazenar o corpo da resposta
            if (responseContext.getEntity() != null) {
                record.responseBody = responseContext.getEntity().toString();
            }

            // Persistir o registro
            record.persist();
        }
    }

    private boolean isIdempotentMethod(String method) {
        return method.equals("GET") || method.equals("HEAD") ||
                method.equals("OPTIONS") || method.equals("TRACE");
    }
}