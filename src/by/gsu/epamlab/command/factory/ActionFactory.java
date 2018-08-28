package by.gsu.epamlab.command.factory;

import by.gsu.epamlab.command.EmptyCommand;
import by.gsu.epamlab.command.enums.TaskCommandEnum;
import by.gsu.epamlab.command.Constants;
import by.gsu.epamlab.command.ifaces.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter(Constants.KEY_COMMAND);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            TaskCommandEnum currentEnum = TaskCommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action + Constants.WRONG_ACTION);

        }
        return current;
    }

}
