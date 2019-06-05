package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // define variables
        Map<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("order", order.getOrder().getLineItemList());
        additionalVariables.put("totalPrice", order.getOrder().getTotalPrice());

        renderTemplate(req, resp, "/payment.html", additionalVariables);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        OrderDao order = OrderDaoMem.getInstance();

        String paymentMethod = req.getParameter("payment");

        //compare selected value
        if ("transfer".equals(paymentMethod)) {
            order.getOrder().setPaymentMethod("transfer");
        } else if ("card".equals(paymentMethod)) {
            order.getOrder().setPaymentMethod("card");
        } else if ("paypal".equals(paymentMethod)) {
            order.getOrder().setPaymentMethod("paypal");
        }

        System.out.println(order.getOrder().getPaymentMethod());

        resp.sendRedirect("/confirmation");
    }
}
