package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        renderTemplate(req, resp, "/checkout.html", null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String city = req.getParameter("city");
        String country = req.getParameter("country");
        String zipCode = req.getParameter("zip-code");
        String phoneNumber = req.getParameter("tel");
        String[] createAccount = req.getParameterValues("create-account");
        String password = req.getParameter("password");
        String shipAddress = req.getParameter("ship-address");
        String shipCity = req.getParameter("ship-city");
        String shipCountry = req.getParameter("ship-country");
        String shipZipCode = req.getParameter("ship-zip-code");
        String[] shippingAddress = new String[]{shipAddress, shipZipCode, shipCity, shipCountry};
        String[] billingAddress = new String[]{address, zipCode, city, country};

        /* TODO
        * if doesCustomerExist(String email)
        *   this.customer = CustomerWithMail
        * else
        *   this.customer = new Customer()*/
    }
}
