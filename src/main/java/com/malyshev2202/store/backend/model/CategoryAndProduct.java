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
    private String categoryName;
    @Indexed
    private Long productId;
    public static long iterator=1;

    public CategoryAndProduct(String categoryName, Long productId) {

        this.categoryName = categoryName;
        this.productId = productId;
    }

    public CategoryAndProduct() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
