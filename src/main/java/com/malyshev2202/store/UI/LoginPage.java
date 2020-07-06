package com.malyshev2202.store.UI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.servlet.http.HttpServletRequest;


@Route(value = "login")
public class LoginPage extends VerticalLayout {
    private Button login = new Button("Вход");
    private EmailField mail = new EmailField("Ввведите ваш email");
    private PasswordField password = new PasswordField("Введите ваш пароль");
    private Anchor regLink = new Anchor("http://localhost:8080/reg");

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private HttpServletRequest req;
    public LoginPage() {
        VerticalLayout layout = new VerticalLayout(mail, password, login, regLink);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);
        login.addClickListener(e -> {
            final UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(mail.getValue(),
                    password.getValue());
            try {
                Authentication auth = authManager.authenticate(authReq);
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);
            }
            catch (final AuthenticationException ex) {
                String message = "Incorrect user or password: " + ex.getMessage()+" " + mail.getValue() + ":" + password.getValue();
                Notification.show(message);
        }});

    }
}

