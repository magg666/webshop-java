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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
    public Map<String, Object> makeMapOfOrderVariables(){
        OrderDao order = OrderDaoMem.getInstance();

        Map<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("order", order.getOrder().getLineItemList());
        additionalVariables.put("totalPrice", order.getOrder().getTotalPrice());
        return additionalVariables;
    }


    public void sendMail(String customerEmail) {
        try {
            final String fromEmail = "webshopcodecool@gmail.com"; //requires valid gmail id
            final String password = "codecool666"; // correct password for gmail id

            System.out.println("TLSEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(customerEmail));

            message.setSubject("Your order in Hot Cart");
            message.setText("You ordered things, thank you");

            Transport.send(message);
        } catch (Exception ex) {
            System.out.println("Mail fail");
            System.out.println(ex);
        }

    }
}
