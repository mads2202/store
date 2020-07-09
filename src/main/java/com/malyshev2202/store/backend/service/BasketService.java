package com.malyshev2202.store.backend.service;

import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.repo.BasketItemRepo;
import com.malyshev2202.store.backend.repo.BasketRepo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BasketService {

    private final BasketItemRepo basketItemRepo;
    private final CustomUserDetailsService userDetailsService;

    public BasketService(BasketItemRepo bir, CustomUserDetailsService uds) {
        this.basketItemRepo = bir;
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
        for (BasketItem item : basketItemRepo.findByBasket(basket.getId())) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        return totalPrice;
    }
}
