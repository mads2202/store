package com.malyshev2202.store.backend.service;

import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.Product;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BasketService {
    //получение даты создания корзины
    public Date getCurrentDate()
    {
        Date date= new Date();
        return date;
    }
    //подсчёт общей стоимости корзины
    public double getTotalPrice(Basket basket){

        return 1;
    }
}
