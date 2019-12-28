package com.codecool.shop.controller;

import com.codecool.shop.dao.AllOrdersDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.db_implementation.AllOrdersDaoJDBC;
import com.codecool.shop.dao.db_implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.db_implementation.ProductDaoJDBC;
import com.codecool.shop.model.Customer;
import com.codecool.shop.service.PageCoordinator;
import com.codecool.shop.service.SessionManager;
import com.codecool.shop.util.Message;
import com.codecool.shop.util.OrderManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class OrderController extends HttpServlet {
    /**
     * Method get render template for order
     * @param req
     * @param resp
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao products = new ProductDaoJDBC();
        ProductCategoryDao categories = new ProductCategoryDaoJDBC();

        if (PageCoordinator.doesUserWantToLookAround(req)) {
            resp.sendRedirect("/");
        } else {
            PageCoordinator.goToRequestedPage(req, resp, "/orderTemplate.html", products, categories);

        }SessionManager.clearAllMessages(req);
    }

    /**
     * Method post for order page. It gets given parameters form template and (depends of parameter):
     * 1. removed product by one from cart using remove button
     * 2. changes quantity of product - using inputs
     * 3. saves unfinished order and shows appropriate message for user
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdToDecreaseByOne = req.getParameter("removeOne");
        String newQuantity = req.getParameter("quantity");
        String productId = req.getParameter("productId");
        String saveOrder = req.getParameter("saveOrder");
        OrderDao currentOrder = SessionManager.getOrderFromSession(req);
        Customer loggedCustomer = SessionManager.getCustomerFromSession(req);
        AllOrdersDao allOrders = new AllOrdersDaoJDBC();

        try {
            if (productIdToDecreaseByOne != null) {
                OrderManager.removeFromOrderByTrashButton(productIdToDecreaseByOne, currentOrder);
            } else if (newQuantity != null && productId != null) {
                OrderManager.changeProductQuantity(productId, newQuantity, currentOrder);
            } else if (saveOrder != null && loggedCustomer != null) {
                if (currentOrder.countProducts() > 0) {
                    OrderManager.saveOrder(loggedCustomer, currentOrder, allOrders);
                    SessionManager.setMessageForUser(req, Message.CART_SAVED.getMessage());
                } else {
                    SessionManager.setMessageForUser(req, Message.EMPTY_CART.getMessage());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            resp.sendRedirect("/cart");
        }
    }
}
