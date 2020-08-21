package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.service.BasketService;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.malyshev2202.store.backend.strategy.CassandraStrategy;
import com.malyshev2202.store.backend.strategy.DBStrategy;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


@Route(value = "login")
public class LoginPage extends VerticalLayout {
    private final BasketService basketService;
    private final CustomUserDetailsService userDetailsService;
    private final DBStrategy strategy;
    private Button login = new Button("Вход");
    private EmailField mail = new EmailField("Ввведите ваш email");
    private PasswordField password = new PasswordField("Введите ваш пароль");
    private Anchor regLink = new Anchor("http://localhost:8080/reg", "Regestration");
    private Anchor forgetPassword = new Anchor("http://localhost:8080/forget", "Forget password?");

    @Autowired
    private AuthenticationManager authManager;



    public LoginPage(DBStrategy dbStrategy,CustomUserDetailsService uds, BasketService bs) {
        this.basketService = bs;
        this.userDetailsService = uds;
        this.strategy=dbStrategy;

        VerticalLayout layout = new VerticalLayout(mail, password, login, regLink, forgetPassword);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);
        login.setIcon(VaadinIcon.UNLOCK.create());
        // метод логина пользователя
        login.addClickListener(e -> {
            final UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(mail.getValue(),
                    password.getValue());
            try {
                Authentication auth = authManager.authenticate(authReq);
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);
                Basket basket = new Basket(userDetailsService.getCurrentUser().getId(), basketService.getCurrentDate());
                if(strategy instanceof CassandraStrategy){
                    basket.setId(Basket.iterator);
                    Basket.iterator++;

                }

                strategy.saveBasket(basket);
                UI.getCurrent().navigate("");
            } catch (final AuthenticationException ex) {
                String message = "Incorrect user or password: " + ex.getMessage() + " " + mail.getValue() + ":" + password.getValue();
                Notification.show(message);
            }
        });
        login.addClickShortcut(Key.ENTER);

    }
}

