package org.pedrojaraujo;

import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@Provider
public class MyApiKeyFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String expectedApiKey = "123456";
        String providedApiKey = requestContext.getHeaderString("x-api-key");

        if (providedApiKey == null || !providedApiKey.equals(expectedApiKey)) {
            throw new UnauthorizedException();
        }
    }
}
