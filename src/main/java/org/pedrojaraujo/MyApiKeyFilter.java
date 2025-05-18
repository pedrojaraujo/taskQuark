package org.pedrojaraujo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.pedrojaraujo.repositories.UserRepository;
import java.util.logging.Logger;

@Provider
@ApplicationScoped
public class MyApiKeyFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(MyApiKeyFilter.class.getName());

    @ConfigProperty(name = "quarkus.api-key.header-name", defaultValue = "x-api-key")
    String apiKeyHeader;

    @Inject
    UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext){
        String path = requestContext.getUriInfo().getPath();

        // Verificar se é uma rota pública
        if (isPublicRoute(path)) {
            return;
        }

        // Obter o valor do cabeçalho da API key
        String providedKey = requestContext.getHeaderString(apiKeyHeader);

        // Verificar se a API key existe e é válida
        if (providedKey == null || userRepository.count("apiKey", providedKey) == 0) {
            LOG.warning("Tentativa de acesso com API key inválida ou ausente: " + path);
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("API key inválida ou ausente")
                            .build());
        }
    }

    private boolean isPublicRoute(String path) {
        // Rotas públicas que não requerem autenticação
        return path.contains("/public/")
                || path.startsWith("/health")
                || path.startsWith("/metrics")
                || path.startsWith("/swagger")
                || path.startsWith("/q/")
                || path.startsWith("/auth/");
    }
}