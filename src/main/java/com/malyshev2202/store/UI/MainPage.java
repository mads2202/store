package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.component.ProductEditor;
import com.malyshev2202.store.backend.model.Product;
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
import org.springframework.util.StringUtils;

@Route("")
public class MainPage extends VerticalLayout {
    private final ProductRepo repo;
    private final ProductEditor editor;
    private TextField filter;
    private Grid<Product> productGrid = new Grid<>(Product.class);
    private Button profile;
    private Button bascket;
    private final Button addNewBtn=new Button("New product", VaadinIcon.PLUS.create());


    @Autowired
    public MainPage(ProductRepo r, ProductEditor editor) {
        this.repo = r;
        this.editor = editor;
        this.profile = new Button("Profile", VaadinIcon.USER.create());
        this.bascket = new Button("Basket", VaadinIcon.CART_O.create());
        this.filter=new TextField();
        filter.setPlaceholder("Find by name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> findProduct(e.getValue()));
        MenuBar menuBar=new MenuBar();
        MenuItem menu=menuBar.addItem(VaadinIcon.MENU.create());
        productGrid.setItems(repo.findAll());
        productGrid.setColumns("name","description","price","number");
        HorizontalLayout topButtons=new HorizontalLayout(menuBar,filter,profile,bascket,addNewBtn);
        add(topButtons,productGrid,editor);
        productGrid.asSingleSelect().addValueChangeListener(e -> {
            editor.editProduct(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editProduct(new Product()));
        bascket.addClickListener(e-> UI.getCurrent().navigate("basket"));
        profile.addClickListener(e-> UI.getCurrent().navigate("profile"));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            findProduct(filter.getValue());
        });
        findProduct(null);

    }

    public void findProduct(String filterText){
        if(StringUtils.isEmpty(filterText)){
            productGrid.setItems(repo.findAll());
        }
        else productGrid.setItems(repo.findByName(filterText));

    }

}