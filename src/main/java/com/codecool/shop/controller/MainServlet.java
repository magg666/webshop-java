package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MainServlet extends HttpServlet {


    void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String template, Map<String, Object> params) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("departments", productCategoryDataStore.getAllDepartments());
        context.setVariable("products", productDataStore.getAll());

        context.setVariables(params);
        engine.process(template, context, resp.getWriter());
//        System.out.println("query "+req.getQueryString());
//        System.out.println("pathTrans "+req.getPathTranslated());
//        System.out.println("paramsNames "+req.getParameterNames());

    }


}
