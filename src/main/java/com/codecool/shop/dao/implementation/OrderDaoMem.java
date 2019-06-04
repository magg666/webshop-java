package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

public class OrderDaoMem implements OrderDao {

    private Order order = new Order();
    private static OrderDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
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

    public void addById(int id){
        LineItem lineItem = getLineItemById(id);
        if(lineItem != null){
            order.add(lineItem);
        }
    }

    private LineItem getLineItemById(int id){
        ProductDao productDao = ProductDaoMem.getInstance();
        Product product = productDao.find(id);
        return new LineItem(product);
    }

}
