package com.malyshev2202.store.backend.strategy;

import com.malyshev2202.store.backend.model.*;
import com.malyshev2202.store.backend.repo.cassandraRepo.*;

import com.malyshev2202.store.backend.strategy.DBStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(value = "cassandra.enabled", havingValue = "true")
public class CassandraStrategy implements DBStrategy {
    private final CategoryAndProductDAO categoryAndProductDAO;
    private final BasketItemDAO basketItemDAO;
    private final BasketDAO basketDAO;
    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;
    private final UserDao userDao;

    public CassandraStrategy(BasketItemDAO bir, BasketDAO br, CategoryDAO cr,
                             ProductDAO pr, UserDao ur, CategoryAndProductDAO capr) {
        this.basketItemDAO = bir;
        this.categoryAndProductDAO = capr;
        this.basketDAO = br;
        this.categoryDAO = cr;
        this.productDAO = pr;
        this.userDao = ur;
    }

    @Override
    public List<Product> findAllProducts() {
        return  (List) productDAO.findAll();
    }

    @Override
    public List<Product> findProductByName(String name) {
        return productDAO.findAllByNameContaining(name);
    }

    @Override
    public List<BasketItem> findBasketItemByBasket(Long id) {
        return basketItemDAO.findAllByBasketId(id);
    }

    @Override
    public List<BasketItem> findBasketItemByProduct(Long id) {
        return basketItemDAO.findAllByProductId(id);
    }

    @Override
    public Basket findBasketByUser(String name) {
        Basket basket=basketDAO.findAllByUserId(userDao.findByEmail(name).getId()).get(0);
        for(Basket b: basketDAO.findAllByUserId(userDao.findByEmail(name).getId())){
            if(b.getDate().after(basket.getDate()));
            basket=b;
        }
        return basket;
    }

    @Override
    public void saveBasket(Basket basket) {
        basketDAO.save(basket);
    }

    @Override
    public List<Category> findAllCategories() {
        return (List) categoryDAO.findAll();
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryDAO.findByName(name);
    }

    @Override
    public void saveBasketItem(BasketItem basketItem) {
        basketItemDAO.save(basketItem);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public BasketItem findBasketItemById(Long id) {
        return basketItemDAO.findById(id).get();
    }

    @Override
    public void deleteBasketItem(BasketItem basketItem) {
        basketItemDAO.delete(basketItem);
    }

    @Override
    public void deleteProduct(Product product) {
        productDAO.delete(product);
    }

    @Override
    public void saveProduct(Product product) {
        productDAO.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return productDAO.findById(id).get();
    }

    @Override
    public List<Long> findProductByCategoryName(String name) {
        List <Long> list=new ArrayList<>();
        for (CategoryAndProduct cap:categoryAndProductDAO.findAllByCategoryName(name)){
            list.add(cap.getProductId());
        }

        return list;
    }

    @Override
    public void saveCategoryAndProduct(CategoryAndProduct categoryAndProduct) {
        categoryAndProductDAO.save(categoryAndProduct);
    }

    @Override
    public void deleteCategoryAndProductById(Long id) {
        for (CategoryAndProduct cap:categoryAndProductDAO.findByProductId(id)){
            categoryAndProductDAO.delete(cap);
        }
    }
}
