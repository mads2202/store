package com.malyshev2202.store.backend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
    @Transient
    private HashSet<BasketItem> basketItemSet=new HashSet<>();
    private double totalPrice;
    private Date date;

    public Basket() {
    }

    public Basket(User customer,Date date) {
        this.user = customer;
        this.date=date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashSet<BasketItem> getBasketItemSet() {
        return basketItemSet;
    }

    public void setBasketItemSet(HashSet<BasketItem> basketItemSet) {
        this.basketItemSet = basketItemSet;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
