package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDao order = OrderDaoMem.getInstance();

        // define custom variables
        Map<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("cart", order.getOrder());

        // define parameters for template
        String productId = req.getParameter("product_id");
        String department = req.getParameter("department");
        String productsCategory = req.getParameter("cat");

        // adding to cart
        if (productId != null) {
            order.addById(Integer.parseInt(productId));
        }

        // searching page by departments and categories
        if (department == null) {
            renderTemplate(req, resp, "/store.html", additionalVariables);
        } else {
            composeProductsDivision(req, resp, additionalVariables, department, productsCategory);
        }
    }


}
