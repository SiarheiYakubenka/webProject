package by.gsu.epamlab.command;

import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.factories.DAOFactory;
import by.gsu.epamlab.model.impl.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FixCommand implements by.gsu.epamlab.command.ifaces.ActionCommand {
    private static final Logger LOGGER = Logger.getLogger(FixCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request) {
        String success = "true";
        String strIds = request.getParameter(Constants.KEY_ID);
        String[] ids = strIds.split(",");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(by.gsu.epamlab.controllers.Constants.KEY_USER);
        String userName = user.getName();
        ITaskDAO taskDAO = DAOFactory.getDAO(ITaskDAO.class);
        try {
            for (String strId : ids) {
                int id = Integer.parseInt(strId);
                taskDAO.fix(userName, id);
            }
        } catch (DaoException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE,  e.toString(), e);
            success = "false";
        }
        return success;
    }
}
