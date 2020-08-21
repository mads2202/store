package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.service.MailSender;
import com.malyshev2202.store.backend.strategy.DBStrategy;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;

import java.util.Random;


@Route("forget")
public class PasswordRecoveryPage extends VerticalLayout {
    private final DBStrategy strategy;
    private Label label = new Label("Для востановления пароля введите ваш email, новый пароль будет выслан вам на почту");
    private final MailSender mailSender;
    private EmailField email = new EmailField();
    private Button sendButton = new Button("send");

    public PasswordRecoveryPage(MailSender ms, DBStrategy dbStrategy) {
        this.strategy=dbStrategy;
        this.mailSender = ms;
        add(label, email, sendButton);
        sendButton.addClickListener(e -> {
            if (strategy.findUserByEmail(email.getValue()) != null) {
                Random random = new Random();
                int newPassword = random.nextInt();
                mailSender.send(email.getValue(), "new password", "Hello your new password is " + newPassword);
                User user=strategy.findUserByEmail(email.getValue());
                user.setPassword(Integer.toString(newPassword));
                strategy.saveUser(user);
            }
            else Notification.show("Пользователя с таким email не существует");
            UI.getCurrent().navigate("login");
        });
    }
}
