package by.gsu.epamlab.command;

import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.factories.DAOFactory;
import by.gsu.epamlab.model.impl.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateCommand implements by.gsu.epamlab.command.ifaces.ActionCommand {
    private static final Logger LOGGER = Logger.getLogger(UpdateCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request) {
        String success = "true";
        String description = request.getParameter(Constants.KEY_DESCRIPTION);
        String completionsDate = request.getParameter(Constants.KEY_DATE);
        String strId = request.getParameter(Constants.KEY_ID);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(by.gsu.epamlab.controllers.Constants.KEY_USER);
        String userName = user.getName();
        ITaskDAO taskDAO = DAOFactory.getDAO(ITaskDAO.class);
        Task task = new Task();
        task.setDescription(description);
        task.setCompletionsDate(completionsDate);
        try {
            task.setId(Integer.parseInt(strId));
            taskDAO.edit(userName, task);
        } catch (DaoException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE,  e.toString(), e);
            success = "false";
        }
        return success;
    }
}
