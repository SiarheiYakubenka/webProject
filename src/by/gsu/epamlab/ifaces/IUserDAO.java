package by.gsu.epamlab.ifaces;

import by.gsu.epamlab.model.beans.Role;
import by.gsu.epamlab.model.beans.User;

public interface IUserDAO {
    User getUser(String name, String password);
    boolean addUser(String name, String password);
}
