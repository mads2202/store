package com.malyshev2202.store.backend.repo;

import com.malyshev2202.store.backend.model.Category;
import com.malyshev2202.store.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    @Query(value = "from Category c where c.name=:name")
    public Category findByName(@Param("name") String name);

}
