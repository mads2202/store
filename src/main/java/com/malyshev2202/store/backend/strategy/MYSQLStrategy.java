package com.malyshev2202.store.backend.strategy;

import com.malyshev2202.store.backend.model.*;
import com.malyshev2202.store.backend.repo.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
@ConditionalOnProperty(value="mysql.enabled",havingValue = "true")
@Component
public class MYSQLStrategy implements DBStrategy {
    private final BasketItemRepo basketItemRepo;
    private final BasketRepo basketRepo;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
     public MYSQLStrategy(BasketItemRepo bir,BasketRepo br,CategoryRepo cr, ProductRepo pr, UserRepo ur){
         this.basketItemRepo=bir;
         this.basketRepo=br;
         this.categoryRepo=cr;
         this.productRepo=pr;
         this.userRepo=ur;
     }

    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> findProductByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<BasketItem> findBasketItemByBasket(Long id) {
        return basketItemRepo.findByBasket(id);
    }

    @Override
    public List<BasketItem> findBasketItemByProduct(Long id) {
        return basketItemRepo.findByProductId(id);
    }

    @Override
    public Basket findBasketByUser(String name) {
        return basketRepo.findByCustomer(name);
    }
    @Override
    public void saveBasket(Basket basket){
         basketRepo.save(basket);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public void saveBasketItem(BasketItem basketItem) {
        basketItemRepo.save(basketItem);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public BasketItem findBasketItemById(Long id) {
        return basketItemRepo.findById(id).get();
    }

    @Override
    public void deleteBasketItem(BasketItem basketItem) {
        basketItemRepo.delete(basketItem);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepo.findById(id).get();
    }
}
