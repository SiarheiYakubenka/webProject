package by.gsu.epamlab.bll.command.factory;

import by.gsu.epamlab.bll.command.CommandException;
import by.gsu.epamlab.bll.command.enums.TaskCommandEnum;
import by.gsu.epamlab.bll.ConstantsBLL;
import by.gsu.epamlab.bll.command.ifaces.ActionCommand;
import by.gsu.epamlab.controllers.SessionRequestContent;

public class ActionFactory {

    public ActionCommand defineCommand(SessionRequestContent content) {
        String action = content.getParameter(ConstantsBLL.KEY_COMMAND);
        try {
            TaskCommandEnum currentEnum = TaskCommandEnum.valueOf(action.toUpperCase());
            return currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CommandException(action + ConstantsBLL.WRONG_ACTION, e);
        }
    }

}
