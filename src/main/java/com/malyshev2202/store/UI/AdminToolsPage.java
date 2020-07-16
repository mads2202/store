package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.component.GeneralButtonsComponent;
import com.malyshev2202.store.backend.component.ProductEditor;
import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.repo.ProductRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.util.StringUtils;

@Route("adminToolProduct")
public class AdminToolsPage extends VerticalLayout {
    private TextField filter;
    private final GeneralButtonsComponent buttonsComponent;
    private final ProductRepo productRepo;
    private Grid<Product> productGrid = new Grid<>(Product.class);
    private final ProductEditor editor;
    private final Button addNewBtn = new Button("Новый товар", VaadinIcon.PLUS.create());

    public AdminToolsPage(ProductRepo r,ProductEditor pe, GeneralButtonsComponent gbc){
        this.productRepo = r;
        this.buttonsComponent=gbc;
        this.editor=pe;
        this.filter = new TextField();
        filter.setPlaceholder("Искать по имени");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> findProduct(e.getValue()));
        HorizontalLayout layout = new HorizontalLayout(filter,addNewBtn,buttonsComponent);
        add(layout,productGrid,editor);
        productGrid.setColumns("name", "description", "price", "number");
        productGrid.setItems(productRepo.findAll());
        //когда товар выбран запускай редактирование
        productGrid.asSingleSelect().addValueChangeListener(e -> {
            editor.editProduct(e.getValue());
        });
        addNewBtn.addClickListener(e -> editor.editProduct(new Product()));
        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editProduct(new Product()));
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            findProduct(filter.getValue());
        });

    }
    public void findProduct(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            productGrid.setItems(productRepo.findAll());
        } else productGrid.setItems(productRepo.findByName(filterText));
    }

}
