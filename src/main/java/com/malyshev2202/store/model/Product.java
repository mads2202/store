package com.malyshev2202.store.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

import java.util.Set;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;
    private String description;
    @ManyToMany
    private Set<ProductCategory> category;

    private double price;


    public Product() {
    }

    public Product(String productName,
                   String description, Double price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
