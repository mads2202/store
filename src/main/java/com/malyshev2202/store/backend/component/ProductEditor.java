package com.malyshev2202.store.backend.component;

import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.model.Category;
import com.malyshev2202.store.backend.model.CategoryAndProduct;
import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.strategy.CassandraStrategy;
import com.malyshev2202.store.backend.strategy.DBStrategy;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
public class ProductEditor extends VerticalLayout implements KeyNotifier {
    private MultiSelectListBox<String> listBox = new MultiSelectListBox<>();
    private final DBStrategy strategy;
    private Product product;
    private CategoryAndProduct categoryAndProduct;
    private Binder<Product> binder = new Binder<Product>(Product.class);
    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private TextField name = new TextField("Product Name");
    private TextField description = new TextField("Description");
    private TextField price = new TextField("Price");
    private TextField number = new TextField("Number");
    private TextField imagePath= new TextField("Image Path");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public ProductEditor(DBStrategy dbStrategy) {
        this.strategy=dbStrategy;
        listBox.setItems(getCategoryNames());
        add(name, description, price, number,imagePath, listBox, actions);
        setSpacing(true);
        binder.forField(name).bind(Product::getName, Product::setName);
        binder.forField(description).bind(Product::getDescription, Product::setDescription);
        binder.forField(price).withConverter(new StringToDoubleConverter("Must enter a double"))
                .bind(Product::getPrice, Product::setPrice);
        binder.forField(number).withConverter(new StringToIntegerConverter("Must enter a integer"))
                .bind(Product::getNumber, Product::setNumber);
        binder.forField(imagePath).bind(Product::getImagePath,Product::setImagePath);
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
            product = strategy.findProductById(p.getId());

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
        for (BasketItem item : strategy.findBasketItemByProduct(product.getId())) {
            strategy.deleteBasketItem(item);
        }
        strategy.deleteCategoryAndProductById(product.getId());
        strategy.deleteProduct(product);
        changeHandler.onChange();
    }

    //сохранение товара
    void save() {
        if(strategy instanceof CassandraStrategy){
            product.setId(Product.iterator);
            Product.iterator++;
        }
        strategy.saveProduct(product);
        for(Category c:getSelectedCategories()){
            categoryAndProduct=new CategoryAndProduct(c.getName(), product.getId());
            if(strategy instanceof CassandraStrategy){
                categoryAndProduct.setId(CategoryAndProduct.iterator);
                CategoryAndProduct.iterator++;
            }


            strategy.saveCategoryAndProduct(categoryAndProduct);

        }
        changeHandler.onChange();
    }

    // ChangeHandler is notified when either save or delete
    // is clicked
    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public List<String> getCategoryNames() {
        List<String> names = new ArrayList<>();
        for (Category category : strategy.findAllCategories()
        ) {
            names.add(category.toString());
        }
        return names;
    }

    public Set<Category> getSelectedCategories() {
        Set<Category> selectedCategories = new HashSet<>();
        for (String s : listBox.getSelectedItems()
        ) {
            selectedCategories.add(strategy.findCategoryByName(s));
        }
        return selectedCategories;
    }

}