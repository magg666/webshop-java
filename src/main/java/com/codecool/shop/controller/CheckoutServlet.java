package com.codecool.shop.controller;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CustomerDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Customer;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        OrderDao order = OrderDaoMem.getInstance();
        CustomerDao allCustomers = CustomerDaoMem.getInstance();

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


        if (allCustomers.doesCustomerExist(email)) {
            int currentCustomerId = allCustomers.getCustomerId(email);
            // put as much in CustomerDaoMem. add order to customer.listOfOrders
//            allCustomers.findById(currentCustomerId).getListOfOrders().add((Order) order);
//            allCustomers.addOrderToCustomerById(currentCustomerId, order);
            order.addCustomerId(currentCustomerId);

        } else {
            Customer customer = new Customer(firstName, lastName, billingAddress, shippingAddress, phoneNumber, email);

            allCustomers.addCustomer(customer);
            order.addCustomerId(customer.getId());
        }

        resp.sendRedirect("/payment");
    }
}
