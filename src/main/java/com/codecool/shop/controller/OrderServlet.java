package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class OrderServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDao order = OrderDaoMem.getInstance();

        Map<String, Object> orderVariables = makeMapOfOrderVariables();

        // define parameters for template
        String department = req.getParameter("department");
        String productsCategory = req.getParameter("cat");
        String lineItemId = req.getParameter("remove");

        // removing from cart
        if (lineItemId != null) {
            order.removeById(Integer.parseInt(lineItemId));
            resp.sendRedirect("/cart");
        }

        // searching page by departments and categories
        if (department == null) {
            renderTemplate(req, resp, "/cart.html", orderVariables);
        } else {
            composeProductsDivision(req, resp, orderVariables, department, productsCategory);
        }
    }
}
