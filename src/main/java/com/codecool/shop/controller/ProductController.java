package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get necessary instances
        // try put it in main and get to it by 'super'?
        CartDao order = CartDaoMem.getInstance();

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

//        else {
//            if (productsCategory != null) {
//                int categoryId = Integer.parseInt(productsCategory);
//                List <Product> productsByCategory = productCategoryDataStore.find(categoryId).getProducts();
//                params.put("category", productCategoryDataStore.find(categoryId).getName());
//                params.put("productsByCategories", productsByCategory);
//                renderTemplate(req, resp, "/categoryTemplate.html", params);
//            } else {
//                params.put("categoriesInDepartments", productCategoryDataStore.getByDepartments(department));
//                params.put("productsByDepartments", productDataStore.getByDepartments(department));
//                renderTemplate(req, resp, "/departmentsTemplate.html", params);
//            }
//        }
    }
}
