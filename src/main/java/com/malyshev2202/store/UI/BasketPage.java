package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.repo.BasketItemRepo;
import com.malyshev2202.store.backend.repo.BasketRepo;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

import java.util.HashMap;

@Route("basket")
public class BasketPage extends VerticalLayout {
    private final BasketItemRepo repo;
    private final BasketRepo basketRepo;
    private final CustomUserDetailsService userDetailsService;
    private Grid<BasketItem> basketGrid=new Grid(BasketItem.class);
    public BasketPage(BasketItemRepo bri, CustomUserDetailsService uds, BasketRepo br){
        this.basketRepo=br;
        this.repo=bri;
        this.userDetailsService=uds;

        basketGrid.setItems(repo.findByBasket(basketRepo.findByCustomer(userDetailsService.getCurrentUsername()).getId()));


        add(basketGrid);
    }
}
