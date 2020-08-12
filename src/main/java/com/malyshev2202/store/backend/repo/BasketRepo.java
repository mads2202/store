package com.malyshev2202.store.backend.repo;

import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketRepo extends JpaRepository<Basket, Long> {
    //поиск последней созданной для этого пользователя корзины
    @Query(value = "from Basket b where b.user=(select u.id from User u where u.email=:name)" +
            "and b.date=(select max(bb.date) from Basket bb where b.user=bb.user)")
    public Basket findByCustomer(@Param("name") String name);
}

