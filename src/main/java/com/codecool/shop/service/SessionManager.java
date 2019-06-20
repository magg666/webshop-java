package com.codecool.shop.service;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class manages session on web shop page. It provides specific methods for page and unifies
 * terms and variables naming
 */
public class SessionManager {

    /**
     * Method to put current order in session (or create a new)
     * @param req
     */
    static void setOrderInSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.isNew() || session.getAttribute("order") == null) {
            OrderDao order = OrderDaoMem.getInstance();
            session.setAttribute("order", order);
        }
    }

    /**
     * Gets order from session
     * @param req
     * @return
     */
    public static OrderDao getOrderFromSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        setOrderInSession(req);
        return (OrderDao) session.getAttribute("order");

    }

    /**
     * Sets customer in session
     * @param req
     * @param customer
     */
    public static void setCustomerInSession(HttpServletRequest req, Customer customer){
        HttpSession session = req.getSession();
        session.setAttribute("customer", customer);
    }

    /**
     * Gets customer form session
     * @param req
     * @return
     */
    public static Customer getCustomerFromSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        return (Customer) session.getAttribute("customer");
    }

    /**
     * Set message for user in session
     * @param req
     * @param message
     */
    public static void setMessageForUser(HttpServletRequest req, String message){
        HttpSession session = req.getSession();
        session.setAttribute("messageForUser", message);
    }

}
