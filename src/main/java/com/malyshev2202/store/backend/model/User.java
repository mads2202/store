package com.malyshev2202.store.backend.model;


import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import javax.persistence.*;


@Entity
@javax.persistence.Table(name = "usr")
@org.springframework.data.cassandra.core.mapping.Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PrimaryKey
    private Long id;
    @Indexed
    private String email;
    private String password;
    private String role;
    public static long iterator=2;


    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return role;
    }

    public void setRoles(String role) {
        this.role = role;
    }
}
