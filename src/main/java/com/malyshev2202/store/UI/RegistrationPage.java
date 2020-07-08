package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.model.UserRole;
import com.malyshev2202.store.backend.repo.UserRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Route("reg")
public class RegistrationPage extends VerticalLayout {
    private final UserRepo repo;
    private TextField email = new TextField("Email");
    private PasswordField password = new PasswordField("Пароль");
    private PasswordField repeat = new PasswordField("Повторите пароль");
    private Button regButton = new Button("Зарегистрироваться");
    private Label label = new Label("Заполните все поля для регистрации");

    @Autowired
    public RegistrationPage(UserRepo r) {
        // добавляю кнопочки и прочие
        this.repo = r;
        regButton.setIcon(VaadinIcon.ENTER.create());
        regButton.getElement().getThemeList().add("primary");
        setHorizontalComponentAlignment(Alignment.CENTER);
        add(label, email, password, repeat, regButton);
        setAlignItems(Alignment.CENTER);
        regButton.addClickListener(e -> regNewUser());
        regButton.addClickShortcut(Key.ENTER);

    }

    // метод по регистрации нового пользователя
    public void regNewUser() {
        if (password.getValue().isEmpty() || repeat.getValue().isEmpty() || email.getValue().isEmpty()
        ) {
            label.setText("Заполните все поля формы ");
        } else if (!password.getValue().equals(repeat.getValue())) {
            label.setText("Пароли должны совпадать");
        } else if (repo.findByEmail(email.getValue()) != null) {
            label.setText("Пользователь с таким email уже зарегистрирован");
        } else {
            User user = new User(email.getValue(), password.getValue());
            user.setRoles(Collections.singleton(UserRole.USER));
            repo.save(user);
            UI.getCurrent().navigate("login");

        }

    }
}
