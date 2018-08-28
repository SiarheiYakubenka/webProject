package by.gsu.epamlab.controllers;

import by.gsu.epamlab.command.factory.ActionFactory;
import by.gsu.epamlab.command.ifaces.ActionCommand;
import by.gsu.epamlab.ifaces.BaseController;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.command.enums.TaskKindEnum;
import by.gsu.epamlab.model.impl.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
            TaskKindEnum taskKind = TaskKindEnum.getKind(request);
            List<Task> tasks = taskKind.getList(request);
            if (tasks != null) {
                Gson gsonBuilder = new GsonBuilder().create();
                String json = gsonBuilder.toJson(tasks);
                response.setContentType(Constants.TYPE_JSON);
                response.setCharacterEncoding(Constants.UTF_8);
                response.getWriter().write(json);
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void proccesRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String success = "false";
        try {
            ActionFactory client = new ActionFactory();
            ActionCommand command = client.defineCommand(request);
            success = command.execute(request);
        } catch (ServletException | IOException | DaoException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        String json = "[{\"ok\":" + success + "}]";
        response.setContentType(Constants.TYPE_JSON);
        response.setCharacterEncoding(Constants.UTF_8);
        response.getWriter().write(json);

    }

}
