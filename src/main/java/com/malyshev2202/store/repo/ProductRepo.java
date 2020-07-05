package com.malyshev2202.store.repo;

import com.malyshev2202.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("from Product p" + " where p.productName like concat( '%',:name,'%')")
    List<Product> findByProductName(@Param("name") String name);
}
