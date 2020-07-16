package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.component.BasketItemEditor;
import com.malyshev2202.store.backend.component.GeneralButtonsComponent;
import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.repo.BasketItemRepo;
import com.malyshev2202.store.backend.repo.BasketRepo;
import com.malyshev2202.store.backend.repo.ProductRepo;
import com.malyshev2202.store.backend.service.BasketService;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("basket")
public class BasketPage extends VerticalLayout {
    private final ProductRepo productRepo;
    private final GeneralButtonsComponent generalButtonsComponent;
    private final BasketService basketService;
    private final BasketItemRepo basketItemRepo;
    private final BasketRepo basketRepo;
    private final CustomUserDetailsService userDetailsService;
    private Grid<BasketItem> basketGrid = new Grid(BasketItem.class);
    private Button payButton = new Button("Оплатить");
    private TextField totalPrice;
    private final BasketItemEditor basketItemEditor;

    public BasketPage(BasketItemEditor bii, ProductRepo pr, GeneralButtonsComponent gbc, BasketItemRepo bri, CustomUserDetailsService uds, BasketRepo br, BasketService bs) {
        this.basketItemEditor = bii;
        this.productRepo = pr;
        this.generalButtonsComponent = gbc;
        this.basketService = bs;
        this.basketRepo = br;
        this.basketItemRepo = bri;
        this.userDetailsService = uds;
        Basket basket = basketRepo.findByCustomer(userDetailsService.getCurrentUsername());
        this.totalPrice = new TextField("Сумма");
        totalPrice.setEnabled(false);
        totalPrice.setValue("" + basketService.getTotalPrice(basket));
        payButton.setIcon(VaadinIcon.WALLET.create());
        basketGrid.setItems(basketItemRepo.findByBasket(basketRepo.findByCustomer(userDetailsService.getCurrentUsername()).getId()));
        basketGrid.setColumns("name", "price", "quantity");
        add(generalButtonsComponent, basketGrid, payButton, totalPrice, basketItemEditor);
        generalButtonsComponent.getBasket().setVisible(false);
        payButton.addClickListener(e -> {
            basket.setTotalPrice(basketService.getTotalPrice(basket));
            basketRepo.save(basket);

            basketRepo.save(new Basket(userDetailsService.getCurrentUser(), basketService.getCurrentDate()));
            UI.getCurrent().getPage().reload();
            Notification.show("Товары оплачены. Спасибо что пользуетесь нашем магазином");
        });
        basketGrid.asSingleSelect().addValueChangeListener(e -> {
            basketItemEditor.editBasketItem(e.getValue());
        });
        basketItemEditor.setChangeHandler(() -> {
            basketItemEditor.setVisible(false);
            basketGrid.setItems(basketItemRepo.findByBasket(basket.getId()));
        });
    }
}
