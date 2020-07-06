package com.malyshev2202.store.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String name;
    @Transient
    private Set<ProductCategory> category;
    private String description;
    @NotEmpty
    private double price;
    private int number;

    public Product() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public Set<ProductCategory> getCategory() {
        return category;
    }

    public void setCategory(Set<ProductCategory> category) {
        this.category = category;
    }
}
