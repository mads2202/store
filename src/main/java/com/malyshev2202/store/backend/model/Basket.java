package com.malyshev2202.store.backend.model;

import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Basket {
    @Id
    @PrimaryKey
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Indexed
    private Long userId;
    private double totalPrice;
    private Date date;


    public Basket() {
    }

    public Basket(Long userId, Date date) {
        this.userId = userId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
