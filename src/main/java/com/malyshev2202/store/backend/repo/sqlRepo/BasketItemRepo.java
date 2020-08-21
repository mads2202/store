package com.malyshev2202.store.backend.repo.sqlRepo;

import com.malyshev2202.store.backend.model.BasketItem;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {
    public List<BasketItem> findByBasketId(Long id);
    public List<BasketItem> findByProductId(Long id);

}
