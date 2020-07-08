package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.component.ProductEditor;
import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.repo.BasketRepo;
import com.malyshev2202.store.backend.repo.ProductRepo;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

@Route("")
public class MainPage extends VerticalLayout {
    private final ProductRepo repo;
    private final ProductEditor editor;
    private TextField filter;
    private Grid<Product> productGrid = new Grid<>(Product.class);
    private Button profile;
    private Button bascket;
    private final Button addNewBtn = new Button("Новый товар", VaadinIcon.PLUS.create());
    private Button addToBasket = new Button("Добавить в корзину");
    private Button logout = new Button("Выйти");
    private final BasketRepo brepo;


    @Autowired
    public MainPage(ProductRepo r, ProductEditor editor, BasketRepo br) {
        //Добавление кнопочек и прочего
        this.brepo = br;
        this.repo = r;
        this.editor = editor;
        this.profile = new Button("Профиль", VaadinIcon.USER.create());
        this.bascket = new Button("Корзина", VaadinIcon.CART_O.create());
        this.filter = new TextField();
        filter.setPlaceholder("Искать по имени");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> findProduct(e.getValue()));
        MenuBar menuBar = new MenuBar();
        MenuItem menu = menuBar.addItem(VaadinIcon.MENU.create());
        productGrid.setItems(repo.findAll());
        productGrid.setColumns("name", "description", "price", "number");
        HorizontalLayout topButtons = new HorizontalLayout(menuBar, filter, profile, bascket, logout, addNewBtn);
        HorizontalLayout bottomButtons = new HorizontalLayout(addToBasket);
        bottomButtons.setVisible(false);
        add(topButtons, productGrid, editor, bottomButtons);

        //когда товар выбран запускай редактирование
        productGrid.asSingleSelect().addValueChangeListener(e -> {
            editor.editProduct(e.getValue());
        });
        // покажи редактор товаров если товар выбран
        productGrid.addSelectionListener(e -> {
            if (productGrid.getSelectedItems() != null)
                bottomButtons.setVisible(true);
            else
                bottomButtons.setVisible(false);
        });

        //Если продукт выбран покажи кнопку добавления в корзину
        productGrid.addSelectionListener(e -> {
            if (productGrid.getSelectedItems() != null)
                addToBasket.setVisible(true);
            else
                addToBasket.setVisible(false);
        });

        //todo:логика добавления в корзину
        addToBasket.addClickListener(e -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            productGrid.getSelectedItems();
        });


        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editProduct(new Product()));
        // логика перехода на страницу "Корзина" по нажатию кнопки
        bascket.addClickListener(e -> UI.getCurrent().getPage().open("basket"));
        // логика перехода на страницу "Профиль" по нажатию кнопки
        profile.addClickListener(e -> UI.getCurrent().getPage().open("profile"));
        //логика логаута по нажатию кнопки
        logout.addClickListener(e -> requestLogout());

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            findProduct(filter.getValue());
        });
        findProduct(null);

    }

    // метод поиска товаров по имени
    public void findProduct(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            productGrid.setItems(repo.findAll());
        } else productGrid.setItems(repo.findByName(filterText));
    }

    // метод логаута
    void requestLogout() {
        SecurityContextHolder.clearContext();
        UI.getCurrent().getPage().reload();// to redirect user to the login page
    }

}