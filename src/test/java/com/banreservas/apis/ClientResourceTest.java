package com.banreservas.apis;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.banreservas.entities.Client;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import java.util.List;
import java.util.stream.Stream;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@QuarkusTest
public class ClientResourceTest {
    
    @Test
    public void obtain_all_clients() {
        PanacheMock.mock(Client.class);
        
        Client client = createClient();
        List<PanacheEntityBase> result = Stream.of((PanacheEntityBase)client).toList();
        Uni<List<PanacheEntityBase>> uni = (Uni<List<PanacheEntityBase>>)Uni.createFrom().item(result);
        Mockito.when(Client.listAll(Mockito.any())).thenReturn(uni);

        given()
          .when().get("/clients")
          .then()
            .statusCode( 200)
             .body("[0].id", is(client.id.intValue()), 
                "[0].names", is(client.names),
                "[0].lastnames", is(client.lastnames),
                "[0].address", is(client.address),
                "[0].businessName", is(client.businessName),
                "[0].country", is(client.country),
                "[0].phone", is(client.phone),
                "[0].email", is(client.email)
             );
    }

    @Test
    public void create_a_new_client() {
        Client client = createClient();
        
        given()
            .header("Content-Type", "application/json")
            .body(client)
            .when()
            .post("/clients")
            .then()
            .statusCode( 201)
            .body("id", is(client.id.intValue()), 
                "names", is(client.names),
                "lastnames", is(client.lastnames),
                "address", is(client.address),
                "businessName", is(client.businessName),
                "country", is(client.country),
                "phone", is(client.phone),
                "email", is(client.email)
             );
    }

    @Test
    public void update_a_client() {
        PanacheMock.mock(Client.class);
        
        Client client = createClient();
        Uni<PanacheEntityBase> uni = (Uni<PanacheEntityBase>)Uni.createFrom().item((PanacheEntityBase)client);
        Mockito.when(Client.findById(Mockito.any())).thenReturn(uni);
        
        given()
            .header("Content-Type", "application/json")
            .body(client)
            .when()
            .put("/clients/{id}", 1)
            .then()
            .statusCode( 200)
            .body("id", is(client.id.intValue()), 
                "names", is(client.names),
                "lastnames", is(client.lastnames),
                "address", is(client.address),
                "businessName", is(client.businessName),
                "country", is(client.country),
                "phone", is(client.phone),
                "email", is(client.email)
             );
    }

    @Test
    public void delete_a_client() {
        PanacheMock.mock(Client.class);
        Mockito.when(Client.deleteById(Mockito.any())).thenReturn(Uni.createFrom().item(true));

        given()
            .when()
            .header("Content-Type", "application/json")
            .delete("/clients/{id}", 1)
            .then()
            .statusCode( 200);
    }

    @Test
    public void delete_fails_when_not_found_a_client() {
        PanacheMock.mock(Client.class);
        Mockito.when(Client.deleteById(Mockito.any())).thenReturn(Uni.createFrom().item(false));

        given()
            .when()
            .header("Content-Type", "application/json")
            .delete("/clients/{id}", 2)
            .then()
            .statusCode( 404);
    }

    private Client createClient() {
        Client client = new Client();
        client.id = 1L;
        client.names= "Joseito Vib";
        client.lastnames= "Elejo Car Ai Mama";
        client.address= "Banreservas";
        client.businessName= "jvelejo@banreservas.com";
        client.country= "27 de febrero esq. Maximo Gomez #301";
        client.phone= "8099985555";
        client.email= "British Indian Ocean Territory";
        return client;
    }

}
