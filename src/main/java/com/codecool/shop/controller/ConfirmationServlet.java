package com.codecool.shop.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/thank-you"})
public class ConfirmationServlet extends MainServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        Map<String, Object> additionalVariables = makeMapOfOrderVariables();;
        additionalVariables.put("name", session.getAttribute("name"));

        renderTemplate(req, resp, "/confirmationTemplate.html", additionalVariables);
    }

}
