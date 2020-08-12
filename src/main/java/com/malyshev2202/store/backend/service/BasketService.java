package com.malyshev2202.store.backend.service;

import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.repo.BasketItemRepo;
import com.malyshev2202.store.backend.repo.BasketRepo;
import com.malyshev2202.store.backend.strategy.DBStrategy;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BasketService {

    private final DBStrategy strategy;
    private final CustomUserDetailsService userDetailsService;

    public BasketService(DBStrategy dbStrategy, CustomUserDetailsService uds) {
        this.strategy = dbStrategy;
        this.userDetailsService = uds;
    }

    //получение даты создания корзины
    public Date getCurrentDate() {
        Date date = new Date();
        return date;
    }

    //подсчёт общей стоимости корзины
    public double getTotalPrice(Basket basket) {
        double totalPrice = 0;
        for (BasketItem item : strategy.findBasketItemByBasket(basket.getId())) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        return totalPrice;
    }
}
