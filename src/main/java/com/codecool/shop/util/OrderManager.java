package com.codecool.shop.util;

import com.codecool.shop.dao.AllOrdersDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Customer;

/**
 * Class for handling cart/order of user on order page
 * It can be used for validation of unfinished orders,
 * removing items from cart or changing its quantity
 */
public class OrderManager {

    /**
     * Method to remove products from order by button
     * Method validates parameter provided by request - productId. If type of productId is wrong
     * throws IllegalArgumentException, else remove product from current order
     *
     * @param productIdToDecreaseByOne - String, parameter provided by request
     * @param order - current order
     */
    public static void removeFromOrderByTrashButton(String productIdToDecreaseByOne, OrderDao order){
        if (Validator.isNotNumber(productIdToDecreaseByOne)) {
            throw new IllegalArgumentException(Message.WRONG_TYPE.getMessage());
        }
        order.removeProductById(Integer.parseInt(productIdToDecreaseByOne));
    }

    /**
     * Method validates and controls inputs from user. This input defines expected quantity of given product
     * If input is incorrect, method throws IllegalArgumentException
     *
     * @param productId - String, parameter provided by request
     * @param newQuantity - String, parameter provided by request
     * @param order - current order
     */
    public static void changeProductQuantity(String productId, String newQuantity, OrderDao order){
        if (Validator.isNotNumber(newQuantity) || newQuantity.length() > 4) {
            throw new IllegalArgumentException(Message.WRONG_TYPE.getMessage());
        }
        order.changeQuantityOfProduct(Integer.parseInt(productId), Integer.parseInt(newQuantity));
    }

    /**
     * Method gets current order, assigns customers to it and saves.
     * After that clears current order
     *
     * @param customer - logged customer
     * @param currentOrder - current order
     * @param allOrders - all orders
     */
    public static void saveOrder(Customer customer, OrderDao currentOrder, AllOrdersDao allOrders){
        int customerId = customer.getId();
            currentOrder.getOrder().setCustomerId(customerId);
//            allOrders.addFullOrderAndReturn(currentOrder.getOrder());
            currentOrder.clearOrder();
    }
}
