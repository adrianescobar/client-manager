package com.banreservas.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Client extends PanacheEntity {
    @Column(length = 40)
    public String names;
    @Column(length = 40)
    public String lastnames;
    @Column(length = 40)
    public String businessName;
    @Column(length = 40)
    public String email;
    @Column(length = 60)
    public String address;
    @Column(length = 10)
    public String phone;
    @Column(length = 100)
    public String country;
}
