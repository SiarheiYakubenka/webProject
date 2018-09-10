package by.gsu.epamlab.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute(Constants.KEY_USER, null);
        session.invalidate();
        response.sendRedirect(request.getContextPath() + Constants.NAME_PROJECT_ROOT);
    }


}
