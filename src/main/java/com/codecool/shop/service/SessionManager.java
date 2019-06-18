package com.codecool.shop.service;

import com.codecool.shop.dao.OrderDao;

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
     * @param order - current order for client
     */
    public static void setOrderInSession(HttpServletRequest req, OrderDao order) {
        HttpSession session = req.getSession();
        if (session.isNew() || session.getAttribute("order") == null) {
            session.setAttribute("order", order);
        }
    }

}
