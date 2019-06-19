package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.db_implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.db_implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.PageCoordinator;
import com.codecool.shop.service.SessionManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends MainServlet {
    private String uri; // full path with current parameters


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao products = new ProductDaoJDBC();
        ProductCategoryDao categories = new ProductCategoryDaoJDBC();

        OrderDao currentOrder = OrderDaoMem.getInstance();
        SessionManager.setOrderInSession(req, currentOrder);

        if (PageCoordinator.hasUserWantedToLookAround(req)) {
            PageCoordinator.navigateAmongProducts(req, resp, products, categories);
        } else {
            PageCoordinator.goToRequestedPage(req, resp, "/storeTemplate.html", products, categories);
        }

        uri = req.getRequestURL() + "?" + req.getQueryString();
//
//        // define custom variables
//        Map<String, Object> additionalVariables = new HashMap<>();
//        additionalVariables.put("cart", order.getOrder());
//
//        // define parameters for template
////        String productId = req.getParameter("product_id");
//        String department = req.getParameter("department");
//        String productsCategory = req.getParameter("cat");
//
////        // adding to cart
////        if (productId != null) {
////            order.addById(Integer.parseInt(productId));
////        }
//
//        // searching page by departments and categories
//        if (department == null) {
//            renderTemplate(req, resp, "/store.html", additionalVariables);
//        } else {
//            composeProductsDivision(req, resp, additionalVariables, department, productsCategory);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//
//        String productId = req.getParameter("addLineItem");
//
//        // adding to cart
//        if (productId != null) {
//            order.addById(Integer.parseInt(productId));
//        }
//        resp.sendRedirect("/");
//    }

        OrderDao order = SessionManager.getOrderFromSession(req);
        String productId = req.getParameter("add");
        ProductDao allProducts = new ProductDaoJDBC();

        if (productId != null) {
            Product product = allProducts.find(Integer.parseInt(productId));
            order.add(product);
        }
        resp.sendRedirect(uri);
    }
}
