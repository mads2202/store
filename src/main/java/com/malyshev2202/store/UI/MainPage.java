package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.component.GeneralButtonsComponent;
import com.malyshev2202.store.backend.model.Basket;
import com.malyshev2202.store.backend.model.BasketItem;
import com.malyshev2202.store.backend.model.Category;
import com.malyshev2202.store.backend.model.Product;
import com.malyshev2202.store.backend.repo.BasketItemRepo;
import com.malyshev2202.store.backend.repo.BasketRepo;
import com.malyshev2202.store.backend.repo.CategoryRepo;
import com.malyshev2202.store.backend.repo.ProductRepo;
import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//todo: сдалай проверку на то кто зашёл админ или юзер, они разные кнопки должны видеть
@Route("")
public class MainPage extends VerticalLayout implements BeforeEnterObserver {
    private final GeneralButtonsComponent generalButtonsComponent;
    private final ProductRepo productRepo;
    private TextField filter;
    private Grid<Product> productGrid = new Grid<>(Product.class);

    private Button addToBasket = new Button("Добавить в корзину");
    private final BasketRepo basketRepo;
    private final CustomUserDetailsService userDetailsService;
    private final BasketItemRepo basketItemRepo;
    private final CategoryRepo categoryRepo;
    private BasketItem item;


    @Autowired
    public MainPage(CategoryRepo cr, GeneralButtonsComponent gbc, ProductRepo r, BasketRepo br, CustomUserDetailsService uds, BasketItemRepo bir) {
        //Добавление кнопочек и прочего
        this.categoryRepo = cr;
        this.generalButtonsComponent = gbc;
        this.basketItemRepo = bir;
        this.userDetailsService = uds;
        this.basketRepo = br;
        this.productRepo = r;

        this.filter = new TextField();
        filter.setPlaceholder("Искать по имени");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> findProduct(e.getValue()));
        MenuBar menuBar = new MenuBar();
        MenuItem menu = menuBar.addItem(VaadinIcon.MENU.create());
        for (Category category : categoryRepo.findAll()) {
            menu.getSubMenu().addItem(category.getName());
        }
        menu.addClickListener(e -> productGrid.setItems(productRepo.findAll()));
        List<MenuItem> subMenuItems = menu.getSubMenu().getItems();
        for (MenuItem item : subMenuItems
        ) {
            item.addClickListener(e -> {
                List<Product> list=new ArrayList<>();
                for (Product p : productRepo.findAll()
                ) {
                    if(p.getCategory().contains(categoryRepo.findByName(item.getText())))
                    list.add(p);
                }
                productGrid.setItems(list);
            });

    }

        productGrid.setItems(productRepo.findAll());
        productGrid.setColumns("name","description","price");
    HorizontalLayout topButtons = new HorizontalLayout(menuBar, filter, generalButtonsComponent);
        topButtons.setSpacing(true);
        generalButtonsComponent.getReturnToMainPage().

    setVisible(false);

    HorizontalLayout bottomButtons = new HorizontalLayout(addToBasket);
        bottomButtons.setVisible(false);

    add(topButtons, productGrid, bottomButtons);
        productGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

    // покажи редактор товаров если товар выбран
        productGrid.addSelectionListener(e ->

    {
        if (productGrid.getSelectedItems() != null && userDetailsService.getCurrentUser() != null)
            bottomButtons.setVisible(true);
        else
            bottomButtons.setVisible(false);
    });

    //Если продукт выбран покажи кнопку добавления в корзину
        productGrid.addSelectionListener(e ->

    {
        if
        (productGrid.getSelectedItems() != null && userDetailsService.getCurrentUser() != null) {
            addToBasket.setVisible(true);
            List<BasketItem> list = basketItemRepo.findByBasket(basketRepo.findByCustomer(userDetailsService.getCurrentUsername()).getId());
            //пробегаем в цикле по всем выбранным продуктам, но у меня он только 1
            for (Product product : productGrid.getSelectedItems()
            ) {
                //проверка есть ли вообще BasketItem для этой корхины сейчас
                if (!list.isEmpty()) {
                    //пробегаем по всем существующим для этой корзины BasketItem
                    for (BasketItem existedItem : basketItemRepo.findByBasket(basketRepo.
                            findByCustomer(userDetailsService.getCurrentUsername()).getId())
                    ) {
                        //если такой BasketItem существует то увеличьте его количество на 1
                        if (product.getName().equals(existedItem.getName()) && product.getPrice() == existedItem.getPrice()
                                && product.getId() == existedItem.getProduct().getId()) {
                            existedItem.setQuantity(existedItem.getQuantity() + 1);
                            item = existedItem;

                        } else
                            //иначе создайте новый айтем
                            item = new BasketItem(product.getName(), product.getPrice(), 1,
                                    product, basketRepo.findByCustomer(userDetailsService.getCurrentUsername()));
                    }
                }
                //создать новый айтем если мы не прошли проверку
                else
                    item = new BasketItem(product.getName(), product.getPrice(), 1,
                            product, basketRepo.findByCustomer(userDetailsService.getCurrentUsername()));
            }
        } else
            addToBasket.setVisible(false);
    });


        addToBasket.addClickListener(e ->

    {
        Basket basket = basketRepo.findByCustomer(userDetailsService.getCurrentUsername());
        basketItemRepo.save(item);
        basketRepo.save(basket);
        productGrid.deselectAll();
        addToBasket.setVisible(false);


    });


}

    // метод поиска товаров по имени
    public void findProduct(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            productGrid.setItems(productRepo.findAll());
        } else productGrid.setItems(productRepo.findByName(filterText));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}