package com.malyshev2202.store.backend.repo.sqlRepo;

import com.malyshev2202.store.backend.model.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {
    //поиск товара по имени
    @Query(value = "from Product p"+" where p.name like concat( '%',:name,'%')")
    public List<Product> findByName(@Param("name") String name);


}
