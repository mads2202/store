package com.malyshev2202.store.model;

import java.awt.image.BufferedImage;

public class Product {
    private Long productId;
    private BufferedImage productImage;
    private String productName;
    private String description;
    private double price;
    private ProductCategory category;

    public Product() {
    }

    public Product(BufferedImage productImage, String productName,
                   String description, double price) {
        this.productImage = productImage;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public BufferedImage getProductImage() {
        return productImage;
    }

    public void setProductImage(BufferedImage productImage) {
        this.productImage = productImage;
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
