package org.pedrojaraujo;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RateLimit(value = 5, window = 10)
    @Fallback(fallbackMethod = "rateLimitFallBack")
    public Response hello() {
        return Response.ok("Hello from Quarkus REST").build();
    }

    public Response rateLimitFallBack() {
        return Response.status(429)
                .entity("You are doing too many requests. Our limits are 5 requests per 10 seconds.")
                .header("Retry-After", "10")
                .build();
    }
}