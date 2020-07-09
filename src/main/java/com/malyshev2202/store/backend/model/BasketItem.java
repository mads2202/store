package com.malyshev2202.store.backend.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double price;
    private int quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    public BasketItem() {
    }

    public BasketItem(String name, double price, int quantity, Product product, Basket basket) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.basket = basket;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketItem)) return false;
        BasketItem that = (BasketItem) o;
        return Double.compare(that.price, price) == 0 &&
                quantity == that.quantity &&
                name.equals(that.name) &&
                product.equals(that.product) &&
                basket.equals(that.basket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, product, basket);
    }
}

