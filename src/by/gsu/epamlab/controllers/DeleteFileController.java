package by.gsu.epamlab.controllers;

import by.gsu.epamlab.bll.FileLogic;
import by.gsu.epamlab.ifaces.BaseController;
import by.gsu.epamlab.model.impl.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteFileController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(DeleteFileController.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String root = getServletContext().getRealPath(Constants.NAME_PROJECT_ROOT);
        String success = "true";
        try {
            SessionRequestContent content = new SessionRequestContent(request);
            FileLogic.delete(content, root);
        } catch (IllegalArgumentException | DaoException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            success = "false";
        }
        String json = "[{\"ok\":" + success + "}]";
        forwardJSON(response, json);
    }
}
