package com.codecool.shop.controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        renderTemplate(req, resp, "/checkout.html", null);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        System.out.println(firstName);


        if (!firstName.isEmpty()) {
            Map<String, Object> params = new HashMap<>();
            params.put("message", "Super");
            req.getRequestDispatcher("/").forward(req, resp); //wymaga doPost na serverze, do którego się kieruje.mozna przesłać w
            // dupoście parametry
//            resp.sendRedirect("/"); // po prostu przekierowuje
//            renderTemplate(req, resp, "/confirmation.html", params); wyświetla potwierdzenie z messagem

        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("message", "Not good");
            renderTemplate(req, resp, "/confirmation.html", params);
        }


        // if everything is ok{
        // redirect to


    }
}

