package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.db_implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.db_implementation.ProductDaoJDBC;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.PageCoordinator;
import com.codecool.shop.service.SessionManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private String uri; // full path with current parameters

    private void setUri(HttpServletRequest req){
        if(req.getQueryString() != null){
            uri = req.getRequestURL() + "?" + req.getQueryString();
        } else {
            uri = req.getRequestURL().toString();
        }
    }
    private String getUri(){
        return uri;
    }

    /**
     * Method get for main page. Handles navigation among products
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao products = new ProductDaoJDBC();
        ProductCategoryDao categories = new ProductCategoryDaoJDBC();

        if (PageCoordinator.doesUserWantToLookAround(req)) {
            PageCoordinator.navigateAmongProducts(req, resp, products, categories);
        } else {
            PageCoordinator.goToRequestedPage(req, resp, "/storeTemplate.html", products, categories);
        }

        setUri(req);
    }

    /**
     * Method post handles adding products to order and redirect to the same page - by uri
     * Current order is stored in session
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDao order = SessionManager.getOrderFromSession(req);
        String productId = req.getParameter("add");
        ProductDao allProducts = new ProductDaoJDBC();

        if (productId != null) {
            Product product = allProducts.find(Integer.parseInt(productId));
            order.add(product);
        }
        resp.sendRedirect(getUri());
    }
}
