package by.gsu.epamlab.command.ifaces;

import by.gsu.epamlab.controllers.SessionRequestContent;
import by.gsu.epamlab.ifaces.ITaskDAO;

public interface ActionCommand {
    void execute(SessionRequestContent content, ITaskDAO taskDAO);
}
