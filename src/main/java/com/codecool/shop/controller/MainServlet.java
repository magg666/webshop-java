package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainServlet extends HttpServlet {

    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    OrderDao order = OrderDaoMem.getInstance();

    void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String template, Map<String, Object> optionalVariables) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("departments", productCategoryDataStore.getAllDepartments());
        context.setVariable("products", productDataStore.getAll());
        context.setVariable("itemsCounter", order.getOrder().getOrderedItemsQuantity());

        // adds custom variables for servlets - must be given as HashMap<String, Object>
        context.setVariables(optionalVariables);
        engine.process(template, context, resp.getWriter());
    }

    void composeProductsDivision(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> params, String department, String productsCategory) throws IOException {
        if (productsCategory != null) {
            int categoryId = Integer.parseInt(productsCategory);
            List<Product> productsByCategory = productCategoryDataStore.find(categoryId).getProducts();
            params.put("category", productCategoryDataStore.find(categoryId).getName());
            params.put("productsByCategories", productsByCategory);
            renderTemplate(req, resp, "/categoryTemplate.html", params);
        } else {
            params.put("categoriesInDepartments", productCategoryDataStore.getByDepartments(department));
            params.put("productsByDepartments", productDataStore.getByDepartments(department));
            renderTemplate(req, resp, "/departmentsTemplate.html", params);
        }
    }


}
