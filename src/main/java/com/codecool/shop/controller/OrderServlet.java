package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class OrderServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDao order = OrderDaoMem.getInstance();

        Map<String, Object> orderVariables = makeMapOfOrderVariables();

        // define parameters for template
        String department = req.getParameter("department");
        String productsCategory = req.getParameter("cat");

        // searching page by departments and categories
        if (department == null) {
            renderTemplate(req, resp, "/cart.html", orderVariables);
        } else {
            composeProductsDivision(req, resp, orderVariables, department, productsCategory);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDao order = OrderDaoMem.getInstance();
        Util util = new Util();
        String lineItemIdToRemove = req.getParameter("remove");
        String newQuantity = req.getParameter("quantity");
        String lineItemId = req.getParameter("lineItemId");


        // removing from cart by trash button
        if (lineItemIdToRemove != null) {
            order.removeById(Integer.parseInt(lineItemIdToRemove));
            resp.sendRedirect("/cart");
        } else if (util.checkNumber(newQuantity)) {
            LineItem currentLineItem = order.getOrder().getLineItemList().get(Integer.parseInt(lineItemId) - 1);
            currentLineItem.setQuantity(Integer.parseInt(newQuantity));
            currentLineItem.changePriceOfItem();
            resp.sendRedirect("/cart");
        } else {
            order.getOrder().getLineItemList().clear();
            String message = "Do not mess with cart, dude";
            Map<String, Object> additionalVariables = new HashMap<>();
            additionalVariables.put("message", message);
            renderTemplate(req, resp, "/cart.html", additionalVariables);
        }



    }
}
