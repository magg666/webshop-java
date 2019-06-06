package com.codecool.shop.controller;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CustomerDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        renderTemplate(req, resp, "/checkout.html", null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        OrderDao order = OrderDaoMem.getInstance();
        CustomerDao allCustomers = CustomerDaoMem.getInstance();
        Util util = new Util();

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
        String[] checkoutData = new String[]{firstName, lastName, email, address, city, country, zipCode, phoneNumber};
        HttpSession session = req.getSession();


        if (!util.isNotNull(shippingAddress)) {
            System.arraycopy(billingAddress, 0, shippingAddress, 0, shippingAddress.length);
        }
        session.setAttribute("name", firstName + " " + lastName);
        session.setAttribute("address", shippingAddress);
        session.setAttribute("email", email);


        if (util.isNotNull(checkoutData)) {
            if (allCustomers.doesCustomerExist(email)) {
                int currentCustomerId = allCustomers.getCustomerId(email);
                Customer customer = allCustomers.findById(currentCustomerId);
                allCustomers.addOrderToCustomer(customer, order.getOrder());
                order.addCustomerId(currentCustomerId);


            } else {
                Customer customer = new Customer(firstName, lastName, billingAddress, shippingAddress, phoneNumber, email);
                allCustomers.addCustomer(customer);
                allCustomers.addOrderToCustomer(customer, order.getOrder());
                order.addCustomerId(customer.getId());
            }
            resp.sendRedirect("/payment");
        } else {
            String message = "Unfortunately you didn't put right values in fields. Insert correct data to continue";
            Map<String, Object> additionalVariables = new HashMap<>();
            additionalVariables.put("message", message);
            renderTemplate(req, resp, "/checkout.html", additionalVariables);
        }
    }
}
