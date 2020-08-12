package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.model.UserRole;
import com.malyshev2202.store.backend.repo.UserRepo;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.malyshev2202.store.backend.strategy.DBStrategy;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

//этот класс реализует страницу регистрации новых пользователей
@Route("reg")
public class RegistrationPage extends VerticalLayout {
    private final DBStrategy strategy;
    private Checkbox createAdmin = new Checkbox("Admin?");
    private final CustomUserDetailsService userDetailsService;
    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Пароль");
    private PasswordField repeat = new PasswordField("Повторите пароль");
    private Button regButton = new Button("Зарегистрироваться");
    private Label label = new Label("Заполните все поля для регистрации");

    @Autowired
    public RegistrationPage(DBStrategy dbStrategy, CustomUserDetailsService cuds) {
        // добавляю кнопочки и прочие

        this.userDetailsService = cuds;
        this.strategy =dbStrategy;
        regButton.setIcon(VaadinIcon.ENTER.create());
        regButton.getElement().getThemeList().add("primary");
        setHorizontalComponentAlignment(Alignment.CENTER);
        add(label, email, password, repeat, regButton, createAdmin);
        setAlignItems(Alignment.CENTER);
        regButton.addClickListener(e -> regNewUser());
        regButton.addClickShortcut(Key.ENTER);
        createAdmin.setVisible(userDetailsService.getCurrentUser() != null && userDetailsService.getCurrentUser().getRoles().contains(UserRole.ADMIN));

    }

    // метод по регистрации нового пользователя
    public void regNewUser() {
        if (password.getValue().isEmpty() || repeat.getValue().isEmpty() || email.getValue().isEmpty()
        ) {
            Notification.show("Заполните все поля формы ");
        } else if (!password.getValue().equals(repeat.getValue())) {
            Notification.show("Пароли должны совпадать");
        } else if (strategy.findUserByEmail(email.getValue()) != null) {
            Notification.show("Пользователь с таким email уже зарегистрирован");
        } else {
            User user = new User(email.getValue(), password.getValue());
            if (createAdmin.getValue())
                user.setRoles(Collections.singleton(UserRole.ADMIN));
            else
                user.setRoles(Collections.singleton(UserRole.USER));
            strategy.saveUser(user);
            UI.getCurrent().navigate("login");

        }

    }
}
