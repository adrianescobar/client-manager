package com.banreservas.dtos;

import com.banreservas.entities.Client;

public class ClientDto {
    public String names;
    public String lastnames;
    public String businessName;
    public String email;
    public String address;
    public String phone;
    public String country;

    public Client parseToClient() {
        Client client = new Client();
        client.names = names;
        client.lastnames = lastnames;
        client.businessName = businessName;
        client.email = email;
        client.address = address;
        client.phone = phone;
        client.country = country;

        return client;
    }
}
