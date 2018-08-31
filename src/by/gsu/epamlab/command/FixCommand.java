package by.gsu.epamlab.command;

import by.gsu.epamlab.command.ifaces.ActionCommand;
import by.gsu.epamlab.command.ifaces.BaseCommand;
import by.gsu.epamlab.command.ifaces.ITaskCommand;
import by.gsu.epamlab.controllers.SessionRequestContent;
import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.Task;

public class FixCommand extends BaseCommand implements ActionCommand {
    @Override
    public void execute(SessionRequestContent content, ITaskDAO taskDAO) {
        processCommand(content, new ITaskCommand() {
            @Override
            public void action(String userName, Task task) {

            }

            @Override
            public void action(String userName, int... ids) {
                taskDAO.fix(userName, ids);
            }
        });
    }
}
