package by.gsu.epamlab.controllers;

import by.gsu.epamlab.ifaces.BaseController;
import by.gsu.epamlab.ifaces.IUserDAO;
import by.gsu.epamlab.model.beans.Role;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.factories.DAOFactory;
import by.gsu.epamlab.model.impl.DaoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationController extends BaseController {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter(ConstantsJSP.KEY_LOGIN);
        String password = request.getParameter(ConstantsJSP.KEY_PASSWORD);
        String confPassword = request.getParameter(ConstantsJSP.KEY_CONF_PASSWORD);

        if (login == null || password == null || confPassword == null) {
            forwardError(Constants.JUMP_REG, Constants.ERROR_NULL, request, response);
            return;
        }

        login = login.trim();
        password = password.trim();
        confPassword = confPassword.trim();
        if (Constants.KEY_EMPTY.equals(login)
                || Constants.KEY_EMPTY.equals(password)) {
            forwardError(Constants.JUMP_REG, Constants.ERROR_EMPTY, request, response);
            return;
        }

        if (!password.equals(confPassword)) {
            forwardError(Constants.JUMP_REG, Constants.ERROR_PASSWORDS, request, response);
            return;
        }

        IUserDAO userDAO = DAOFactory.getDAO(IUserDAO.class);
        boolean isAdd = false;
        try {
            isAdd = userDAO.addUser(login, password);
        }catch (DaoException e){
            forwardError(Constants.JUMP_REG, Constants.ERROR_SOURCE, request, response);
            return;
        }
        if (isAdd) {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.KEY_USER, new User(login, password, Role.USER));
            response.sendRedirect(request.getContextPath() + Constants.NAME_PROJECT_ROOT);
        } else {
            forwardError(Constants.JUMP_REG, Constants.ERROR_EXIST, request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = request.getParameter(Constants.KEY_ERROR_MESSAGE);
        if (errorMessage == null || errorMessage.isEmpty()) {
            forward(Constants.JUMP_REG, request, response);
        } else {
            forwardError(Constants.JUMP_REG, errorMessage, request, response);
        }
    }
}
