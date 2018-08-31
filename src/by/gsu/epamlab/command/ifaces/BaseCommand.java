package by.gsu.epamlab.command.ifaces;

import by.gsu.epamlab.command.Constants;
import by.gsu.epamlab.command.util.HelperMetods;
import by.gsu.epamlab.controllers.SessionRequestContent;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.impl.DaoException;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseCommand {
    private static final Logger LOGGER = Logger.getLogger(BaseCommand.class.getName());

    public void processCommand(SessionRequestContent content, ITaskCommand taskCmd) {
        String strId = content.getParameter(Constants.KEY_ID);
        try {
            String userName = ((User)content.getSessionAttribute(Constants.KEY_USER)).getName();
            int[] ids = HelperMetods.strToArrInt(strId);
            taskCmd.action(userName, ids);
        } catch (DaoException | NumberFormatException | NullPointerException e) {
            LOGGER.log(Level.SEVERE,  e.toString(), e);
            throw new DaoException(e);
        }
    }

    public void actionCommand(SessionRequestContent content, ITaskCommand taskCmd) {
        String description = content.getParameter(Constants.KEY_DESCRIPTION);
        String completionsDate = content.getParameter(Constants.KEY_DATE);
        String strId = content.getParameter(Constants.KEY_ID);
        String userName = ((User)content.getSessionAttribute(Constants.KEY_USER)).getName();
        Task task = new Task();
        task.setDescription(description);
        task.setCompletionsDate(completionsDate);
        try {
            if (strId != null) {
                task.setId(Integer.parseInt(strId));
            }
            taskCmd.action(userName, task);
        } catch (DaoException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE,  e.toString(), e);
            throw new DaoException(e);
        }

    }
}
