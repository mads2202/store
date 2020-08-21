package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.component.BasketItemEditor;
import com.malyshev2202.store.backend.component.GeneralButtonsComponent;
import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.service.BasketService;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.malyshev2202.store.backend.strategy.CassandraStrategy;
import com.malyshev2202.store.backend.strategy.DBStrategy;
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
    private final DBStrategy strategy;

    private final GeneralButtonsComponent generalButtonsComponent;
    private final BasketService basketService;
    private final CustomUserDetailsService userDetailsService;
    private Grid<BasketItem> basketGrid = new Grid(BasketItem.class);
    private Button payButton = new Button("Оплатить");
    private TextField totalPrice;
    private final BasketItemEditor basketItemEditor;

    public BasketPage(DBStrategy dbStrategy, BasketItemEditor bii, GeneralButtonsComponent gbc, CustomUserDetailsService uds, BasketService bs) {
        this.strategy = dbStrategy;
        this.basketItemEditor = bii;

        this.generalButtonsComponent = gbc;
        this.basketService = bs;


        this.userDetailsService = uds;
        Basket basket = strategy.findBasketByUser(userDetailsService.getCurrentUsername());
        this.totalPrice = new TextField("Сумма");
        totalPrice.setEnabled(false);
        totalPrice.setValue("" + basketService.getTotalPrice(basket));
        payButton.setIcon(VaadinIcon.WALLET.create());
        basketGrid.setItems(strategy.findBasketItemByBasket(strategy.findBasketByUser(userDetailsService.getCurrentUsername()).getId()));
        basketGrid.setColumns("name", "price", "quantity");
        add(generalButtonsComponent, basketGrid, payButton, totalPrice, basketItemEditor);
        generalButtonsComponent.getBasket().setVisible(false);
        payButton.addClickListener(e -> {
            basket.setTotalPrice(basketService.getTotalPrice(basket));
            strategy.saveBasket(basket);
            Basket basket1=new Basket(userDetailsService.getCurrentUser().getId(), basketService.getCurrentDate());
            if(strategy instanceof CassandraStrategy){
                basket1.setId(Basket.iterator);
                Basket.iterator++;
            }

            strategy.saveBasket(basket1);
            UI.getCurrent().getPage().reload();
            Notification.show("Товары оплачены. Спасибо что пользуетесь нашем магазином");
        });
        basketGrid.asSingleSelect().addValueChangeListener(e -> {
            basketItemEditor.editBasketItem(e.getValue());
        });
        basketItemEditor.setChangeHandler(() -> {
            basketItemEditor.setVisible(false);
            basketGrid.setItems(strategy.findBasketItemByBasket(basket.getId()));
        });
    }
}
