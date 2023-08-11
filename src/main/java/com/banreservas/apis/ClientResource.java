package com.banreservas.apis;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse;

import com.banreservas.dtos.ClientDto;
import com.banreservas.entities.Client;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.OK;

@Path("/clients")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {
    
    @GET
    public Uni<List<Client>> get() {
        return Client.listAll(Sort.by("names"));
    }

    @POST
    public Uni<RestResponse<Client>> create(ClientDto createClient) {
        Client client = createClient.parseToClient();
        return Panache.withTransaction(client::persist).replaceWith(RestResponse.status(CREATED, client));
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(Long id, ClientDto client) {
        if (id == null || client == null) {
            return Panache.currentTransaction().map(x -> Response.ok().status(NOT_FOUND).build());
        }

        return Panache
                .withTransaction(() -> Client.<Client> findById(id)
                    .onItem().ifNotNull().invoke(entity -> { 
                        entity.names = client.names;
                        entity.lastnames = client.lastnames;
                        entity.address = client.address;
                        entity.businessName = client.businessName;
                        entity.country = client.country;
                        entity.phone = client.phone;
                        entity.email = client.email;
                    })
                )
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return Panache.withTransaction(() -> Client.deleteById(id))
                .map(deleted -> deleted
                        ? Response.ok().status(OK).build()
                        : Response.ok().status(NOT_FOUND).build());
    }
}
