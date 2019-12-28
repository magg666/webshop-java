package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        HttpSession session = req.getSession();
        String customerEmail = (String) session.getAttribute("email");

        if (util.isPaymentAndTermsChecked(paymentMethod, terms) && util.isChosenPaymentFilled(paymentMethod, cardData, paypalData)) {
            order.setPayment(paymentMethod);
            sendMail(customerEmail);
            resp.sendRedirect("/thank-you");
        } else {
            resp.sendRedirect("/payment");
        }


        //compare selected value
//        if ("direct".equals(paymentMethod)) {
//            order.getOrder().setPaymentMethod("direct");
//            sendMail(customerEmail);
//            resp.sendRedirect("/thank-you");
//        } else if ("card".equals(paymentMethod)) {
//            if (util.isNotNull(cardData)) {
//                order.getOrder().setPaymentMethod("card");
//                sendMail(customerEmail);
//                resp.sendRedirect("/thank-you");
//            }
//        } else if ("paypal".equals(paymentMethod)) {
//            if (util.isNotNull(paypalData)) {
//                order.getOrder().setPaymentMethod("paypal");
//                sendMail(customerEmail);
//                resp.sendRedirect("/thank-you");
//            }
//        } else {
//            System.out.println(paymentMethod);
//            String message = "Unfortunately you didn't put right values in fields. Insert correct data to continue";
//            Map<String, Object> additionalVariables = new HashMap<>();
//            additionalVariables.put("message", message);
//            renderTemplate(req, resp, "/paymentTemplate.html", additionalVariables);
//        }
    }
}
