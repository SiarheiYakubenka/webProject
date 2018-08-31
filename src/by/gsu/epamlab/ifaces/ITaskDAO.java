package by.gsu.epamlab.ifaces;

import by.gsu.epamlab.model.beans.Task;

import java.util.List;

public interface ITaskDAO {
    List<Task> getList(String userName);
    List<Task> getList(String userName, String condition);
    void add(String userName, Task task);
    void edit(String userName, Task task);
    void delete(String userName, int... id);
    void fix(String userName, int... id);
    void recover(String userName, int... id);
    void remove(String userName, int... id);
}
