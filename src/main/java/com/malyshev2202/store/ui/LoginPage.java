package com.malyshev2202.store.ui;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
;
import com.vaadin.flow.component.html.Image;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;

import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Route("login")
public class LoginPage extends VerticalLayout  {
    @Autowired
    private AuthenticationManager authManager;
    private EmailField emailField = new EmailField();
    private PasswordField passwordField = new PasswordField();
    private Label label=new Label("Please login");


     @Autowired
    private HttpServletRequest req;


    public LoginPage() {
        VerticalLayout layout = new VerticalLayout();

        emailField = new EmailField();
        emailField.setLabel("Email");
        emailField.setClearButtonVisible(true);
        emailField.setAutofocus(true);

        passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setRevealButtonVisible(true);

        Image image = new Image("https://im0-tub-ru.yandex.net/i?id=ae75a9dab6ad09d2233b005f847e2b25&n=13",
                "DummyImage");

        Button button = new Button("login");
        button.addClickListener(e->authenticateAndNavigate());
        layout.add(image,label, emailField, passwordField, button);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);
    }
    private void authenticateAndNavigate() {
        /*
        Set an authenticated user in Spring Security and Spring MVC
        spring-security
        */
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(emailField.getValue(), passwordField.getValue());
        try {
            // Set authentication
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            /*
            Navigate to the requested page:
            This is to redirect a user back to the originally requested URL – after they log in as we are not using
            Spring's AuthenticationSuccessHandler.
            */
            HttpSession session = req.getSession(false);
            DefaultSavedRequest savedRequest = (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            String requestedURI = savedRequest != null ? savedRequest.getRequestURI() : "http://localhost:8080/";

            this.getUI().ifPresent(ui -> ui.navigate(StringUtils.removeStart(requestedURI, "/")));

        } catch (BadCredentialsException e) {
            label.setText("eror");


        }
    }


}
