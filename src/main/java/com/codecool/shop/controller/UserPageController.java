package com.codecool.shop.controller;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.db_implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.db_implementation.ProductDaoJDBC;
import com.codecool.shop.model.Customer;
import com.codecool.shop.service.PageCoordinator;
import com.codecool.shop.service.SessionManager;
import com.codecool.shop.util.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user-page/*"})
public class UserPageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao products = new ProductDaoJDBC();
        ProductCategoryDao categories = new ProductCategoryDaoJDBC();
        Customer loggedCustomer = SessionManager.getCustomerFromSession(req);

        if (PageCoordinator.doesUserWantToLookAround(req)) {
            resp.sendRedirect("/");
        } else if(loggedCustomer != null){
            PageCoordinator.goToRequestedPage(req, resp, "/userTemplate.html", products, categories);
            SessionManager.clearAllMessages(req);
        } else {
            SessionManager.setMessageForUser(req,Message.NOT_LOGGED.getMessage());
            resp.sendRedirect("/cart");
        }

    }
}
