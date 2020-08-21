package com.malyshev2202.store.backend.repo.sqlRepo;

import com.malyshev2202.store.backend.model.CategoryAndProduct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryAndProductRepo extends JpaRepository<CategoryAndProduct, String> {

    List<CategoryAndProduct> findAllByCategoryName(String name);
    @Query("from CategoryAndProduct c where c.productId=:id")
    void deleteByProductId(@Param("id") Long id);
}
