package com.banreservas.apis;

import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.banreservas.auth.AuthRequest;
import com.banreservas.auth.AuthResponse;
import com.banreservas.auth.Role;
import com.banreservas.auth.utils.TokenUtils;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration", defaultValue = "3600") public Long duration;
	@ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://clientmanager.com") public String issuer;

    @PermitAll
	@POST @Path("/login")
	public Uni<Response> login(AuthRequest authRequest) {
		return Panache.withTransaction(() -> Uni.createFrom().item(authRequest.username.equals("malagueton") && authRequest.password.equals("claverobusta")) )
        .map(match -> {
            try {
                return match ? Response.ok(new AuthResponse(TokenUtils.generateToken(authRequest.username, Set.of(Role.ADMIN), duration, issuer))).build()
                    : Response.status(Status.UNAUTHORIZED).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Status.UNAUTHORIZED).build();
            }
        });
	}
}
