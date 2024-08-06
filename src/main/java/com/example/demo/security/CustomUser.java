package com.example.demo.security;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class CustomUser {

    @Id
    @Column(name = "username")
    private  String username;

    @Column(name= "password")
    private String password;
}
