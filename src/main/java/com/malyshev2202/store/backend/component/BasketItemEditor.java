package com.malyshev2202.store.backend.component;

import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.strategy.DBStrategy;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class BasketItemEditor extends VerticalLayout implements KeyNotifier {
    private final DBStrategy strategy;
    private BasketItem item;
    Binder<BasketItem> binder = new Binder<BasketItem>(BasketItem.class);
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    TextField quantity = new TextField("Quantity");
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public BasketItemEditor(DBStrategy dbStrategy) {
        this.strategy = dbStrategy;
        add( quantity, actions);
        setSpacing(true);
        binder.forField(quantity).withConverter(new StringToIntegerConverter("Must enter a integer"))
                .bind(BasketItem::getQuantity, BasketItem::setQuantity);
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editBasketItem(item));
        setVisible(false);


    }

    //редактирование продукта
    public void editBasketItem(BasketItem bi) {
        if (bi == null) {
            setVisible(false);
            return;
        }

            item =strategy.findBasketItemById(bi.getId());



        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(item);

        setVisible(true);

        // Focus first name initially
        quantity.focus();
    }

    //удаление товара
    void delete() {
        strategy.deleteBasketItem(item);
        changeHandler.onChange();
    }

    //сохранение товара
    void save() {
        strategy.saveBasketItem(item);
        UI.getCurrent().getPage().reload();
        changeHandler.onChange();
    }

    // ChangeHandler is notified when either save or delete
    // is clicked
    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}