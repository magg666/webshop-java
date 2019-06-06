package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Map;

@WebServlet(urlPatterns = {"/thank-you"})
public class ConfirmationServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDao order = OrderDaoMem.getInstance();
        ProductDao productDao = ProductDaoMem.getInstance();
        HttpSession session = req.getSession();

        Map<String, Object> additionalVariables = makeMapOfOrderVariables();
        ;
        additionalVariables.put("name", session.getAttribute("name"));
        additionalVariables.put("address", session.getAttribute("address"));
        additionalVariables.put("email", session.getAttribute("email"));


        try {
            String orderId = String.valueOf(order.getOrder().getId());
            String orderPrice = order.getOrder().getTotalPrice();
            String fileName = "No" + orderId + "Total" + orderPrice + ".json";
            FileWriter writer = new FileWriter(fileName);
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .serializeSpecialFloatingPointValues()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .setPrettyPrinting()
                    .create();
            gson.toJson(order.getOrder(), writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        order.clearOrder();

        renderTemplate(req, resp, "/confirmationTemplate.html", additionalVariables);
    }

}
