package com.banreservas.services;

import java.util.List;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.banreservas.entities.Country;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/all")
@RegisterRestClient(configKey = "countries-api")
public interface CountryService {

    @GET
    CompletionStage<List<Country>> getAllCountries();
}
