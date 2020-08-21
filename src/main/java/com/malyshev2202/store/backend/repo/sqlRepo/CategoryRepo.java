package com.malyshev2202.store.backend.repo.sqlRepo;

import com.malyshev2202.store.backend.model.Category;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CategoryRepo extends JpaRepository<Category,Long> {
    public Category findByName( String name);

}
