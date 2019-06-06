package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentServlet extends MainServlet {



    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> orderVariables = makeMapOfOrderVariables();
        renderTemplate(req, resp, "/paymentTemplate.html", orderVariables);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        OrderDao order = OrderDaoMem.getInstance();

        String paymentMethod = req.getParameter("payment");
        String terms = req.getParameter("terms");





        //compare selected value
        if ("transfer".equals(paymentMethod)) {
            order.getOrder().setPaymentMethod("transfer");
        } else if ("card".equals(paymentMethod)) {
            order.getOrder().setPaymentMethod("card");
        } else if ("paypal".equals(paymentMethod)) {
            order.getOrder().setPaymentMethod("paypal");
        }

        System.out.println(order.getOrder().getPaymentMethod());

        sendMail(req, resp);
        resp.sendRedirect("/thank-you");
    }
}
