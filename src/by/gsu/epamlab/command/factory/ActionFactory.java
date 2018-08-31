package by.gsu.epamlab.command.factory;

import by.gsu.epamlab.command.CommandException;
import by.gsu.epamlab.command.enums.TaskCommandEnum;
import by.gsu.epamlab.command.Constants;
import by.gsu.epamlab.command.ifaces.ActionCommand;
import by.gsu.epamlab.controllers.SessionRequestContent;

public class ActionFactory {

    public ActionCommand defineCommand(SessionRequestContent content) {
        String action = content.getParameter(Constants.KEY_COMMAND);
        try {
            TaskCommandEnum currentEnum = TaskCommandEnum.valueOf(action.toUpperCase());
            return currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CommandException(action + Constants.WRONG_ACTION, e);
        }
    }

}
