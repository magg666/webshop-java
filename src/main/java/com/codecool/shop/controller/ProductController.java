package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("product_id");
        String department = req.getParameter("department");
        ProductDao cart = CartDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();

        if(productId != null){
            cart.add(productDataStore.find(Integer.parseInt(productId)));
        }
        if(department != null){
            renderTemplate(req, resp, "/categoryPage.html", department);
        } else {
            renderTemplate(req,resp,"/store.html", null);
        }



    }

}
