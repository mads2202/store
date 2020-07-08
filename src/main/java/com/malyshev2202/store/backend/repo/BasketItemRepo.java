package com.malyshev2202.store.backend.repo;

import com.malyshev2202.store.backend.model.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BasketItemRepo extends JpaRepository<BasketItem,Long> {
    @Query(value = "from BasketItem bi where bi.basket=(select b.id from Basket b where b.id=:id)")
    public List<BasketItem> findByBasket(@Param("id") Long id);
}
