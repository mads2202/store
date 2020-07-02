package com.malyshev2202.store.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("")
public class MainPage extends VerticalLayout {
    public MainPage() {
        VerticalLayout layout = new VerticalLayout();

        EmailField emailField = new EmailField();
        emailField.setLabel("Email");
        emailField.setClearButtonVisible(true);
        emailField.setAutofocus(true);

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setRevealButtonVisible(true);

        Image image = new Image("https://im0-tub-ru.yandex.net/i?id=ae75a9dab6ad09d2233b005f847e2b25&n=13",
                "DummyImage");

        TextField nickName=new TextField();
        nickName.setLabel("Nickname");

        Button button = new Button("login");

        layout.add(image);
        layout.add(nickName);
        layout.add(emailField);
        layout.add(passwordField);
        layout.add(button);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);


    }
}
