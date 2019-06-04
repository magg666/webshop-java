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
        // get necessary instances
        // try put it in main and get to it by 'super'?
        OrderDao order = OrderDaoMem.getInstance();

        // define variables
        Map<String, Object> params = new HashMap<>();
        params.put("cart", order.getOrder());

        // define parameters for template
        String productId = req.getParameter("product_id");
        String department = req.getParameter("department");
        String productsCategory = req.getParameter("cat");

        // adding to cart
        if (productId != null) {
//            String queryString = req.getQueryString();
//            params.put("query", queryString);
            order.addById(Integer.parseInt(productId));

        }

        // searching page by departments and categories -- to main navigateOnPage
        if (department == null) {
            renderTemplate(req, resp, "/store.html", params);
        }

        else {
            composeProductsDivision(req, resp, params, department, productsCategory);
        }
    }


}
