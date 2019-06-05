package com.codecool.shop.controller;

import javax.servlet.ServletException;
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
        String payment = req.getParameter("payment");
        String terms = req.getParameter("terms");
        System.out.println(payment);
        System.out.println(terms);

        sendMail(req, resp);
        resp.sendRedirect("/thank-you");
    }
}
