package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    WebContext context;



    void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String template, String notYetKnown) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ProductDao cart = CartDaoMem.getInstance();


//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("categoryObject", productCategoryDataStore);
        context.setVariable("departments", productCategoryDataStore.getAllDepartments());
        context.setVariable("products", productDataStore.getAll());
        context.setVariable("cart", cart);
        context.setVariable("productsByDepartments", productCategoryDataStore.getByDepartments(notYetKnown));

        engine.process(template, context, resp.getWriter());
    }

//    void setVariable(String name, Object object){
//        getContext().get;
//
//    }
}
