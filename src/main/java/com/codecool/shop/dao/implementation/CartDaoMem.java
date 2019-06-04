package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

public class CartDaoMem implements CartDao {

    Order order = new Order();
    private static CartDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public Order getOrder(){
        return this.order;
    }

    @Override
     public void removeById(int id){
//        LineItem lineItem = getLineItemById(id);
//        if(lineItem != null){
//            order.remove(lineItem);
//        }

    }

    public void addById(int id, ProductDao productDao){
        LineItem lineItem = getLineItemById(id, productDao);
        if(lineItem != null){
            order.add(lineItem);
        }
    }

    LineItem getLineItemById(int id, ProductDao productDao){
        Product product = productDao.find(id);
        return new LineItem(product);
    }

}
