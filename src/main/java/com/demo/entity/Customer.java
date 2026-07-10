package com.demo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Customer extends PanacheEntity {
    public String name;
    public String phoneNumber;
    public String address;
}
