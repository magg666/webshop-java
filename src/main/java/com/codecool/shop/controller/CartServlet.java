package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class CartServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get necessary instances


        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        CartDao order = CartDaoMem.getInstance();

        // define variables
        Map<String, Object> params = new HashMap<>();
        params.put("departments", productCategoryDataStore.getAllDepartments());
        params.put("products", productDataStore.getAll());
        params.put("cart", order.getOrder().getLineItemList());

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
}
