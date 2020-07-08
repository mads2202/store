package com.malyshev2202.store.backend.model;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User customer;
    @Transient
    private HashMap<Product,Integer> productList;
    private double totalPrice;
    private String date;

    public Basket() {
    }

    public Basket(User customer,String date) {
        this.customer = customer;
        this.date=date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public HashMap<Product, Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<Product, Integer> productList) {
        this.productList = productList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
