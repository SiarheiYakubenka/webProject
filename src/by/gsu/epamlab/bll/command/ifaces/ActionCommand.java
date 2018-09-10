package by.gsu.epamlab.bll.command.ifaces;

import by.gsu.epamlab.controllers.SessionRequestContent;
import by.gsu.epamlab.ifaces.ITaskDAO;

public interface ActionCommand {
    void execute(SessionRequestContent content, ITaskDAO taskDAO);
}
