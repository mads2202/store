package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.repo.UserRepo;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("profile")
public class ProfilePage extends VerticalLayout {
    private final CustomUserDetailsService userDetailsService;
    private final UserRepo repo;
    private TextField email = new TextField("Email");
    private TextField password = new TextField("Пароль");
    private Button changePassword = new Button("Изменить пароль");
    private Label label = new Label("Ваши персональные данные сэр");

    public ProfilePage(UserRepo r, CustomUserDetailsService uds) {
        // кнопочки и инициализация объектов
        this.repo = r;
        this.userDetailsService = uds;
        VerticalLayout layout = new VerticalLayout(label, email, password, changePassword);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);
        //заполняем поле почты, почтой авторизованного пользователя
        email.setValue(userDetailsService.getCurrentUser().getEmail());
        email.setEnabled(false);
        //заполняем поле пароля, паролем авторизованного пользователя
        password.setValue(userDetailsService.getCurrentUser().getPassword());
        password.setEnabled(false);
        //изменение пароля по нажатию кнопки
        changePassword.addClickListener(e -> {
            password.setEnabled(true);
            if (password.getValue().isEmpty()) {
                Notification.show("Для безопастности учётной записи, поле пороля не должно быть пустым");
            } else {
                User user = repo.findByEmail(userDetailsService.getCurrentUsername());
                user.setPassword(password.getValue());
                repo.save(user);
                password.setEnabled(false);
            }
        });

    }

}
