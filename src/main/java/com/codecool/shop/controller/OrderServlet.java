package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class OrderServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDao order = OrderDaoMem.getInstance();

        // define variables
        Map<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("order", order.getOrder().getLineItemList());
        additionalVariables.put("totalPrice", order.getOrder().getTotalPrice());

        // define parameters for template
        String department = req.getParameter("department");
        String productsCategory = req.getParameter("cat");
        String lineItemId = req.getParameter("remove");

        // adding to cart
        if (lineItemId != null) {
            order.removeById(Integer.parseInt(lineItemId));
        }

        // searching page by departments and categories
        if (department == null) {
            renderTemplate(req, resp, "/cart.html", additionalVariables);
        } else {
            composeProductsDivision(req, resp, additionalVariables, department, productsCategory);
        }
    }
}
