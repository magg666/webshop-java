package com.codecool.shop.service;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * this class contains static methods to navigate on web page,
 * unifies naming convention for variables putted into sessions or template
 */
public class PageCoordinator {

    /**
     * Method allows putting variables in web context and renders pointed template, which uses this variables
     * Also method puts order in session - on every page
     *
     * @param req
     * @param resp
     * @param template          - string - path to html file
     * @param optionalVariables - Map where key = String, value = Object
     * @throws IOException
     */
    public static void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String template, Map<String, Object> optionalVariables) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        SessionManager.setOrderInSession(req);

        context.setVariables(optionalVariables);
        engine.process(template, context, resp.getWriter());
    }

    /**
     * This method determine, if user clicked on department/category link
     * If it is true, appropriate parameter(s) is/are getting by request.
     *
     * @param req - Http Servlet request
     * @return true - if user's actions suggest, that he wants to look around products in shop
     */
    public static boolean doesUserWantToLookAround(HttpServletRequest req) {
        String department = req.getParameter("department");
        String productsCategory = req.getParameter("cat");
        return department != null || productsCategory != null;
    }

    /**
     * Method defines variables for page where are shown products by categories.
     * Expected category is indicated by category id
     *
     * @param productsCategory - parameter provided by Http request
     * @param products         - all products
     * @param categories       - all categories
     * @param variables        - Map where key = String, value = Object
     */
    private static void setVariablesForCategoryPage(String productsCategory, ProductDao products, ProductCategoryDao categories, HashMap<String, Object> variables) {
        int categoryId = Integer.parseInt(productsCategory);
        List<Product> productsByCategory = products.getBy(categories.find(categoryId));
        variables.put("products", productsByCategory);
        variables.put("categories", categories.find(categoryId).getName());
        variables.put("departments", categories.getAllDepartments());
    }

    /**
     * Method defines variables for page where are shown products by departments.
     * Expected department is indicated by department name
     * variable "showCategories" sets view of categories in template
     *
     * @param department - parameter provided by Http request
     * @param products   - all products
     * @param categories - all categories
     * @param variables  - Map where key = String, value = Object
     */
    private static void setVariablesForDepartmentPage(String department, ProductDao products, ProductCategoryDao categories, HashMap<String, Object> variables) {
        variables.put("showCategories", 'A');
        variables.put("products", products.getByDepartments(department));
        variables.put("categories", categories.getByDepartments(department));
        variables.put("departments", categories.getAllDepartments());
    }

    /**
     * Method defines variables for main page.
     * It shows all products.
     *
     * @param products   - all products
     * @param categories - all categories
     * @param variables  - Map where key = String, value = Object
     */
    public static void setDefaultVariables(ProductDao products, ProductCategoryDao categories, HashMap<String, Object> variables) {
        variables.put("products", products.getAll());
        variables.put("categories", categories.getAll());
        variables.put("departments", categories.getAllDepartments());
    }

    /**
     * Method to navigate between products by categories and departments.
     * It is adjust to use one and the same template
     * Handles some errors - redirect user to main page if error occurs
     * @param req
     * @param resp
     * @param products
     * @param categories
     * @throws IOException
     */
    public static void navigateAmongProducts(HttpServletRequest req, HttpServletResponse resp, ProductDao products, ProductCategoryDao categories) throws IOException {
        String department = req.getParameter("department");
        String productsCategory = req.getParameter("cat");

        HashMap<String, Object> variables = new HashMap<>();

        try {
            variables.put("department", department);
            if (!categories.getAllDepartments().contains(department)) {
                throw new NoSuchElementException("There is no such department");
            }

            if (productsCategory != null) {
                setVariablesForCategoryPage(productsCategory, products, categories, variables);
            } else {
                setVariablesForDepartmentPage(department, products, categories, variables);
            }
            renderTemplate(req, resp, "/storeTemplate.html", variables);
        } catch (NullPointerException | NumberFormatException | NoSuchElementException e) {
            resp.sendRedirect("/");
        }
    }

    /**
     * This is universal method to show every page in shop. It puts default variables for all pages in web context
     * This method should be used on every page, where user has an access to departments/ categories menu
     * @param req
     * @param resp
     * @param defaultTemplate - string with path to html template
     * @param products - all products
     * @param categories - all categories
     * @throws IOException
     */
    public static void goToRequestedPage(HttpServletRequest req, HttpServletResponse resp, String defaultTemplate, ProductDao products, ProductCategoryDao categories) throws IOException {
        HashMap<String, Object> variables = new HashMap<>();
        setDefaultVariables(products, categories, variables);
        renderTemplate(req, resp, defaultTemplate, variables);
    }


}
