package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.repo.UserRepo;
import com.malyshev2202.store.backend.service.MailSender;
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
    private final UserRepo userRepo;
    private Label label = new Label("Для востановления пароля введите ваш email, новый пароль будет выслан вам на почту");
    private final MailSender mailSender;
    private EmailField email = new EmailField();
    private Button sendButton = new Button("send");

    public PasswordRecoveryPage(MailSender ms, UserRepo ur) {
        this.mailSender = ms;
        this.userRepo = ur;
        add(label, email, sendButton);
        sendButton.addClickListener(e -> {
            if (userRepo.findByEmail(email.getValue()) != null) {
                Random random = new Random();
                int newPassword = random.nextInt();
                mailSender.send(email.getValue(), "new password", "Hello your new password is " + newPassword);
                User user=userRepo.findByEmail(email.getValue());
                user.setPassword(Integer.toString(newPassword));
                userRepo.save(user);
            }
            else Notification.show("Пользователя с таким email не существует");
            UI.getCurrent().navigate("login");
        });
    }
}
