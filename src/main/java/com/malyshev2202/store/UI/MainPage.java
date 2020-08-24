package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.component.GeneralButtonsComponent;
import com.malyshev2202.store.backend.config.CassandraConfig;
import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.model.Category;
import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.malyshev2202.store.backend.strategy.CassandraStrategy;
import com.malyshev2202.store.backend.strategy.DBStrategy;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//todo: сдалай проверку на то кто зашёл админ или юзер, они разные кнопки должны видеть
@Route("")
public class MainPage extends VerticalLayout {
    private final GeneralButtonsComponent generalButtonsComponent;

    private TextField filter;
    private Grid<Product> productGrid = new Grid<>(Product.class);
    private Product product;
    private Button addToBasket = new Button("Добавить в корзину");
    private final CustomUserDetailsService userDetailsService;
    private BasketItem item;
    private Image productImage = new Image();
    private final DBStrategy strategy;


    @Autowired
    public MainPage(DBStrategy dbStrategy, GeneralButtonsComponent gbc, CustomUserDetailsService uds) {
        //Добавление кнопочек и прочего
        this.strategy = dbStrategy;
        this.generalButtonsComponent = gbc;
        this.userDetailsService = uds;


        this.filter = new TextField();
        filter.setPlaceholder("Искать по имени");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> findProduct(e.getValue()));
        MenuBar menuBar = new MenuBar();
        MenuItem menu = menuBar.addItem(VaadinIcon.MENU.create());
        for (Category category : strategy.findAllCategories()) {
            menu.getSubMenu().addItem(category.getName());
        }
        menu.addClickListener(e -> productGrid.setItems(strategy.findAllProducts()));
        List<MenuItem> subMenuItems = menu.getSubMenu().getItems();
        for (MenuItem item : subMenuItems
        ) {
            item.addClickListener(e -> {
                List<Product> list = new ArrayList<>();
                for (Long l: strategy.findProductByCategoryId(strategy.findCategoryByName(item.getText()).getId())
                ) {

                        list.add(strategy.findProductById(l));
                }
                productGrid.setItems(list);
            });

        }

        productGrid.setItems(strategy.findAllProducts());
        productGrid.setColumns("name", "description", "price");
        HorizontalLayout topButtons = new HorizontalLayout(menuBar, filter, generalButtonsComponent);
        topButtons.setSpacing(true);
        generalButtonsComponent.getReturnToMainPage().setVisible(false);


        add(topButtons, productGrid, productImage, addToBasket);
        addToBasket.setVisible(false);
        productImage.setVisible(false);
        productGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        // покажи редактор товаров если товар выбран
        productGrid.addSelectionListener(e -> {
            if (productGrid.getSelectedItems() != null) {
                for (Product p : productGrid.getSelectedItems()
                ) {
                    product = p;
                }
                if (product.getImagePath() != null) {
                    productImage.setSrc(product.getImagePath());
                    productImage.setVisible(true);
                }
            } else
                productImage.setVisible(false);
        });
        productGrid.addSelectionListener(e ->

        {
            if (productGrid.getSelectedItems() != null && userDetailsService.getCurrentUser() != null) {
                for (Product p : productGrid.getSelectedItems()
                ) {
                    product = p;
                }
                if (product.getImagePath() != null)
                    productImage.setSrc(product.getImagePath());
                addToBasket.setVisible(true);
            } else
                addToBasket.setVisible(false);
        });

        //Если продукт выбран покажи кнопку добавления в корзину
        productGrid.addSelectionListener(e ->

        {
            if
            (productGrid.getSelectedItems() != null && userDetailsService.getCurrentUser() != null) {
                addToBasket.setVisible(true);
                List<BasketItem> list = strategy.findBasketItemByBasket(strategy.findBasketByUser(userDetailsService.getCurrentUsername()).getId());
                //пробегаем в цикле по всем выбранным продуктам, но у меня он только 1
                for (Product product : productGrid.getSelectedItems()
                ) {
                    //проверка есть ли вообще BasketItem для этой корхины сейчас
                    if (!list.isEmpty()) {
                        //пробегаем по всем существующим для этой корзины BasketItem
                        for (BasketItem existedItem : strategy.findBasketItemByBasket(strategy.
                                findBasketByUser(userDetailsService.getCurrentUsername()).getId())
                        ) {
                            //если такой BasketItem существует то увеличьте его количество на 1
                            if (product.getName().equals(existedItem.getName()) && product.getPrice() == existedItem.getPrice()
                                    && product.getId() == existedItem.getProductId()) {
                                existedItem.setQuantity(existedItem.getQuantity() + 1);
                                item = existedItem;

                            } else
                                //иначе создайте новый айтем
                                item = new BasketItem(product.getName(), product.getPrice(), 1,
                                        product.getId(), strategy.findBasketByUser(userDetailsService.getCurrentUsername()).getId());
                        }
                    }
                    //создать новый айтем если мы не прошли проверку
                    else
                        item = new BasketItem(product.getName(), product.getPrice(), 1,
                                product.getId(), strategy.findBasketByUser(userDetailsService.getCurrentUsername()).getId());
                }
            } else
                addToBasket.setVisible(false);
        });


        addToBasket.addClickListener(e ->

        {
            Basket basket = strategy.findBasketByUser(userDetailsService.getCurrentUsername());
            if(strategy instanceof CassandraStrategy){
                item.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

            }
            strategy.saveBasketItem(item);
            strategy.saveBasket(basket);
            productGrid.deselectAll();
            addToBasket.setVisible(false);


        });


    }

    // метод поиска товаров по имени
    public void findProduct(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            productGrid.setItems(strategy.findAllProducts());
        } else productGrid.setItems(strategy.findProductByName(filterText));
    }


}