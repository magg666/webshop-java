package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

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
        // get necessary instances


        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        OrderDao order = OrderDaoMem.getInstance();

        // define variables
        Map<String, Object> params = new HashMap<>();
        params.put("departments", productCategoryDataStore.getAllDepartments());
        params.put("products", productDataStore.getAll());
        params.put("order", order.getOrder().getLineItemList());

        // define parameters for template
        String department = req.getParameter("department");
        String productsCategory = req.getParameter("cat");
        String removedProductId = req.getParameter("remove");

        // adding to cart
        if (removedProductId != null) {
//            String queryString = req.getQueryString();
//            params.put("query", queryString);
            order.removeById(Integer.parseInt(removedProductId) );
        }

        // searching page by departments and categories
        if (department == null) {
            renderTemplate(req, resp, "/cart.html", params);
        } else {
            composeProductsDivision(req, resp, params, department, productsCategory);
        }
    }
}
