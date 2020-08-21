package com.malyshev2202.store.backend.repo.sqlRepo;

import com.malyshev2202.store.backend.model.Basket;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface BasketRepo extends JpaRepository<Basket, Long> {
    //поиск последней созданной для этого пользователя корзины
    public List<Basket> findByUserId(Long id);

}

