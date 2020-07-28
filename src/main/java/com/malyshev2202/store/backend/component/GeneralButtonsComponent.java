package com.malyshev2202.store.backend.component;

import com.malyshev2202.store.backend.model.UserRole;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class GeneralButtonsComponent extends VerticalLayout  {
    private Button logout = new Button("Выйти");
    private Button returnToMainPage = new Button("Вернуться на главную страницу");
    private Button profile = new Button("Профиль");
    private Button basket = new Button("Корзина");
    private Button adminTool=new Button("Администраторский инструментарий");
    private final CustomUserDetailsService userDetailsService;

    public GeneralButtonsComponent(CustomUserDetailsService uds) {
        this.userDetailsService = uds;
        returnToMainPage.setIcon(VaadinIcon.ARROW_BACKWARD.create());
        profile.setIcon(VaadinIcon.USER.create());
        basket.setIcon(VaadinIcon.CART_O.create());
        logout.setIcon(VaadinIcon.EXIT_O.create());
        returnToMainPage.addClickListener(e -> {UI.getCurrent().navigate(""); UI.getCurrent().getPage().reload();});
        profile.addClickListener(e -> UI.getCurrent().getPage().open("profile"));
        basket.addClickListener(e -> UI.getCurrent().getPage().open("basket"));
        logout.addClickListener(e -> userDetailsService.requestLogout());
        adminTool.setVisible(userDetailsService.getCurrentUser()!=null && userDetailsService.getCurrentUser().getRoles().contains(UserRole.ADMIN));
        adminTool.addClickListener(e->{UI.getCurrent().getPage().open("adminToolProduct");});
        HorizontalLayout layout = new HorizontalLayout(returnToMainPage, profile, basket, logout, adminTool);
        add(layout);
    }

    public Button getReturnToMainPage() {
        return returnToMainPage;
    }

    public Button getProfile() {
        return profile;
    }

    public Button getBasket() {
        return basket;
    }


}
