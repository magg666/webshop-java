package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentServlet extends MainServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> orderVariables = makeMapOfOrderVariables();
        renderTemplate(req, resp, "/paymentTemplate.html", orderVariables);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        OrderDao order = OrderDaoMem.getInstance();
        Util util = new Util();

        String paymentMethod = req.getParameter("payment");
        String terms = req.getParameter("terms");
        String cardOwner = req.getParameter("cc-owner");
        String cardCvv = req.getParameter("cc-cvv");
        String cardNumber = req.getParameter("cc-cardNumber");
        String cardExpMonth = req.getParameter("cc-exp-month");
        String cardExpYear = req.getParameter("cc-exp-year");
        String paypalMail = req.getParameter("paypal-mail");
        String paypalPassword = req.getParameter("paypal-password");
        String[] paypalData = new String[]{paypalMail, paypalPassword};
        String[] cardData = new String[]{cardOwner, cardCvv, cardNumber, cardExpMonth, cardExpYear};


        //compare selected value
        if ("transfer".equals(paymentMethod)) {
            order.getOrder().setPaymentMethod("transfer");
            sendMail(req, resp);
            resp.sendRedirect("/thank-you");
        } else if ("card".equals(paymentMethod)) {
            if (util.isNotNull(cardData)) {
                order.getOrder().setPaymentMethod("card");
                sendMail(req, resp);
                resp.sendRedirect("/thank-you");
            }
        } else if ("paypal".equals(paymentMethod)) {
            if (util.isNotNull(paypalData)) {
                order.getOrder().setPaymentMethod("paypal");
                sendMail(req, resp);
                resp.sendRedirect("/thank-you");
            }
        }
        resp.sendRedirect("/payment");
    }
}
