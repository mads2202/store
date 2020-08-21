package com.malyshev2202.store.backend.strategy;

import com.malyshev2202.store.backend.model.*;
import com.malyshev2202.store.backend.repo.sqlRepo.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(value = "mysql.enabled", havingValue = "true")
@Component
public class MYSQLStrategy implements DBStrategy {
    private final CategoryAndProductRepo categoryAndProductRepo;
    private final BasketItemRepo basketItemRepo;
    private final BasketRepo basketRepo;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public MYSQLStrategy(BasketItemRepo bir, BasketRepo br, CategoryRepo cr, ProductRepo pr, UserRepo ur, CategoryAndProductRepo capr) {
        this.basketItemRepo = bir;
        this.categoryAndProductRepo = capr;
        this.basketRepo = br;
        this.categoryRepo = cr;
        this.productRepo = pr;
        this.userRepo = ur;
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
        return basketItemRepo.findByBasketId(id);
    }

    @Override
    public List<BasketItem> findBasketItemByProduct(Long id) {
        return basketItemRepo.findByProductId(id);
    }

    @Override
    public Basket findBasketByUser(String name) {
        Basket basket=basketRepo.findByUserId(userRepo.findByEmail(name).getId()).get(0);
        for(Basket b:basketRepo.findByUserId(userRepo.findByEmail(name).getId()))
        {
            if(basket.getDate().before(b.getDate()))
                basket=b;
        }
        return basket;
    }

    @Override
    public void saveBasket(Basket basket) {
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

    @Override
    public List<Long> findProductByCategoryName(String name) {
        List <Long> list=new ArrayList<>();
        for (CategoryAndProduct cap:categoryAndProductRepo.findAllByCategoryName(name)){
            list.add(cap.getProductId());
        }
        return list;
    }

    @Override
    public void saveCategoryAndProduct(CategoryAndProduct categoryAndProduct) {
        categoryAndProductRepo.save(categoryAndProduct);
    }

    @Override
    public void deleteCategoryAndProductById(Long id) {
        categoryAndProductRepo.deleteByProductId(id);
    }
}
