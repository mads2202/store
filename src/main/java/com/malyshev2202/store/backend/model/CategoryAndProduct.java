package com.malyshev2202.store.backend.model;


import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table
public class CategoryAndProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PrimaryKey
    private Long id;
    @Indexed
    private Long categoryId;
    @Indexed
    private Long productId;

    public CategoryAndProduct(Long categoryId, Long productId) {

        this.categoryId = categoryId;
        this.productId = productId;
    }

    public CategoryAndProduct() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
