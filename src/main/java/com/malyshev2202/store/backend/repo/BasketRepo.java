package com.malyshev2202.store.backend.repo;

import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketRepo extends JpaRepository<Basket,Long> {
    //поиск последней созданной для этого пользователя корзины
    @Query(value = "from Product p"+" where p.name like concat( '%',:name,'%')")
    public List<Product> findByName(@Param("name") String name);
}

