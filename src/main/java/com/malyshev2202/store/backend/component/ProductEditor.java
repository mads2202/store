package com.malyshev2202.store.backend.component;

import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.repo.ProductRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class ProductEditor extends VerticalLayout implements KeyNotifier {
    private final ProductRepo repo;
    private Product product;
    Binder<Product> binder = new Binder<Product>(Product.class);
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    TextField name = new TextField("Product Name");
    TextField description = new TextField("Description");
    TextField price = new TextField("Price");
    TextField number = new TextField("Number");
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public ProductEditor(ProductRepo pr) {
        this.repo = pr;
        add(name, description, price, number, actions);
        setSpacing(true);
        binder.forField(name).bind(Product::getName, Product::setName);
        binder.forField(description).bind(Product::getDescription, Product::setDescription);
        binder.forField(price).withConverter(new StringToDoubleConverter("Must enter a double"))
                .bind(Product::getPrice, Product::setPrice);
        binder.forField(number).withConverter(new StringToIntegerConverter("Must enter a integer"))
                .bind(Product::getNumber, Product::setNumber);
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editProduct(product));
        setVisible(false);


    }

    //редактирование продукта
    public void editProduct(Product p) {
        if (p == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = p.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            product = repo.findById(p.getId()).get();
        } else {
            product = p;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(product);

        setVisible(true);

        // Focus first name initially
        name.focus();
    }

    //удаление товара
    void delete() {
        repo.delete(product);
        changeHandler.onChange();
    }

    //сохранение товара
    void save() {
        repo.save(product);
        changeHandler.onChange();
    }

    // ChangeHandler is notified when either save or delete
    // is clicked
    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}