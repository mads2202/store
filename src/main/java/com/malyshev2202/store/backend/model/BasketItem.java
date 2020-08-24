package com.malyshev2202.store.backend.model;

import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
@PrimaryKey
    private Long id;
    private String name;
    private double price;
    private int quantity;
    @Indexed
    private Long productId;
    @Indexed
    private Long basketId;

    public BasketItem() {
    }

    public BasketItem(String name, double price, int quantity, Long productId, Long basketId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
        this.basketId = basketId;
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

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketItem)) return false;
        BasketItem item = (BasketItem) o;
        return Double.compare(item.price, price) == 0 &&
                name.equals(item.name) &&
                productId.equals(item.productId) &&
                basketId.equals(item.basketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, productId, basketId);
    }
}


