package com.banreservas.apis;

import java.util.List;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.banreservas.entities.Country;
import com.banreservas.services.CountryService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/countries")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class CountryResource {

    @Inject
    @RestClient
    private CountryService countryService;

    @RolesAllowed("ADMIN")
    @GET
    public CompletionStage<List<Country>> get(){
        return this.countryService.getAllCountries();
    }
}
