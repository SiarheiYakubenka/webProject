package by.gsu.epamlab.bll;

import by.gsu.epamlab.bll.command.enums.TaskKindEnum;
import by.gsu.epamlab.bll.command.factory.ActionFactory;
import by.gsu.epamlab.bll.command.ifaces.ActionCommand;
import by.gsu.epamlab.controllers.SessionRequestContent;
import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.factories.DAOFactory;

import java.util.List;

public final class TaskLogic {
    public static void execute(SessionRequestContent content){
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(content);
        ITaskDAO taskDAO = DAOFactory.getDAO(ITaskDAO.class);
        command.execute(content, taskDAO);
    }
    public static List<Task> getTasks(SessionRequestContent content) {
        TaskKindEnum taskKind = TaskKindEnum.getKind(content);
        return taskKind.getList(content);
    }
}
