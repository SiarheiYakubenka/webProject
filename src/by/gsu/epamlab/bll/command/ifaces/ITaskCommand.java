package by.gsu.epamlab.bll.command.ifaces;

import by.gsu.epamlab.model.beans.Task;

public interface ITaskCommand {
    void action(String userName, Task task);
    void action(String userName, int... ids);
}
