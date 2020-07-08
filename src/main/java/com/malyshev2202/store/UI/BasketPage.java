package com.malyshev2202.store.UI;

import com.malyshev2202.store.backend.model.Product;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

import java.util.HashMap;

@Route("basket")
public class BasketPage extends VerticalLayout {
    private Grid<HashMap<Product,Integer>> basketGrid=new Grid();
    public BasketPage(){
        basketGrid.setColumns("name", "price");
        add(basketGrid);
    }
}
