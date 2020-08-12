package com.malyshev2202.store.backend.strategy;

import com.malyshev2202.store.backend.model.*;

import java.util.List;

public interface DBStrategy {
    List<Product> findAllProducts();
    List<Product> findProductByName(String name);
    List<BasketItem> findBasketItemByBasket(Long id);
    List<BasketItem> findBasketItemByProduct(Long id);
    Basket findBasketByUser(String name);
    void saveBasket(Basket basket);
    void saveBasketItem(BasketItem basketItem);
    void saveUser(User user);
    void saveProduct(Product product);
    List<Category> findAllCategories();
    Category findCategoryByName(String name);
    User findUserByEmail(String email);
    BasketItem findBasketItemById(Long id);
    void deleteBasketItem(BasketItem basketItem);
    void deleteProduct(Product product);
    Product findProductById(Long id);
}
