package by.gsu.epamlab.bll.command;

import by.gsu.epamlab.bll.command.ifaces.ActionCommand;
import by.gsu.epamlab.bll.command.ifaces.BaseCommand;
import by.gsu.epamlab.bll.command.ifaces.ITaskCommand;
import by.gsu.epamlab.controllers.SessionRequestContent;
import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.Task;

public class UpdateCommand extends BaseCommand implements ActionCommand {
    @Override
    public void execute(SessionRequestContent content, ITaskDAO taskDAO) {

        actionCommand(content, new ITaskCommand() {
            @Override
            public void action(String userName, Task task) {
                taskDAO.edit(userName, task);
            }

            @Override
            public void action(String userName, int... ids) {
            }
        });
    }
}
