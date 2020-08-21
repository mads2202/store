package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.component.GeneralButtonsComponent;
import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.malyshev2202.store.backend.strategy.DBStrategy;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("profile")
public class ProfilePage extends VerticalLayout {
    private final GeneralButtonsComponent generalButtonsComponent;
    private final CustomUserDetailsService userDetailsService;
    private TextField email = new TextField("Email");
    private TextField password = new TextField("Пароль");
    private Button changePassword = new Button("Изменить пароль");
    private Label label = new Label("Ваши персональные данные сэр");
    private Button save=new Button("Сохранить изменения");
    private final DBStrategy strategy;


    public ProfilePage(GeneralButtonsComponent gbc, DBStrategy dbStrategy, CustomUserDetailsService uds) {
        // кнопочки и инициализация объектов
        this.generalButtonsComponent = gbc;
        this.strategy = dbStrategy;
        this.userDetailsService = uds;
        VerticalLayout layout = new VerticalLayout(label, email, password, changePassword,save);
        layout.setAlignItems(Alignment.CENTER);
        add(generalButtonsComponent, layout);
        generalButtonsComponent.getProfile().setVisible(false);
        //заполняем поле почты, почтой авторизованного пользователя
        email.setValue(userDetailsService.getCurrentUser().getEmail());
        email.setEnabled(false);
        //заполняем поле пароля, паролем авторизованного пользователя
        password.setValue(userDetailsService.getCurrentUser().getPassword());
        password.setEnabled(false);
        save.setIcon(VaadinIcon.FILE_ADD.create());

        save.setVisible(false);
        changePassword.setIcon(VaadinIcon.EDIT.create());
        //изменение пароля по нажатию кнопки
        changePassword.addClickListener(e->{password.setEnabled(true);
        save.setVisible(true);
        });
        save.addClickListener(e->{
            if (password.getValue().isEmpty()) {
            Notification.show("Для безопастности учётной записи, поле пороля не должно быть пустым");
        } else {
            User user = strategy.findUserByEmail(userDetailsService.getCurrentUsername());
            user.setPassword(password.getValue());
            strategy.saveUser(user);
            save.setVisible(false);
            password.setEnabled(false);
        }});


    }

}
