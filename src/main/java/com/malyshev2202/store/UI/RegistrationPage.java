package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.repo.UserRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("reg")
public class RegistrationPage extends VerticalLayout {
    private final UserRepo repo;
    private User user;
    private Binder<User> binder = new Binder<>(User.class);
    private TextField email = new TextField("Email");
    private PasswordField password = new PasswordField("Пароль");
    private PasswordField repeat = new PasswordField("Повторите пароль");
    private Button regButton = new Button("Зарегистрироваться");
    private Label label = new Label("Заполните все поля для регистрации");

    @Autowired
    public RegistrationPage(UserRepo r) {
        this.repo = r;
        regButton.setIcon(VaadinIcon.ENTER.create());
        regButton.getElement().getThemeList().add("primary");
        VerticalLayout layout = new VerticalLayout(label, email, password, repeat, regButton);
        add(layout);
        binder.bindInstanceFields(this);

        regButton.addClickListener(e->regNewUser(new User()));
        regButton.addClickShortcut(Key.ENTER);

    }

    public void regNewUser(User u) {
        if (password.getValue().isEmpty() || repeat.getValue().isEmpty() || email.getValue().isEmpty()
                ) {
            label.setText("Заполните все поля формы ");
        } if (!password.getValue().equals(repeat.getValue())){
            label.setText("Пароли должны совпадать");
        }if(repo.findByEmail(email.getValue())!=null){
            label.setText("Пользователь с таким email уже зарегистрирован");
        }else {
            user=u;
            binder.setBean(user);
            repo.save(user);
        }

    }
}
