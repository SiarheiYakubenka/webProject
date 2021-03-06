package by.gsu.epamlab.controllers;

import by.gsu.epamlab.bll.command.CommandException;
import by.gsu.epamlab.bll.TaskLogic;
import by.gsu.epamlab.ifaces.BaseController;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.impl.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(AppController.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        proccesRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String kind = request.getParameter(Constants.KEY_KIND);
        if (kind != null) {
            getTasksJSON(request, response);
        } else {
            forward(Constants.JUMP_TASKS, request, response);
        }
    }

    private void getTasksJSON(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SessionRequestContent content = new SessionRequestContent(request);
            List<Task> tasks = TaskLogic.getTasks(content);
            Gson gsonBuilder = new GsonBuilder().create();
            String json = gsonBuilder.toJson(tasks);
            forwardJSON(response, json);
        } catch (DaoException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void proccesRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String success = "true";
        try {
            SessionRequestContent content = new SessionRequestContent(request);
            TaskLogic.execute(content);
        } catch (CommandException | DaoException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            success = "false";
        }
        String json = "[{\"ok\":" + success + "}]";
        forwardJSON(response, json);

    }


}
