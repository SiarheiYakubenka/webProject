package by.gsu.epamlab.model.factories;

import by.gsu.epamlab.ifaces.IFileDAO;
import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.ifaces.IUserDAO;
import by.gsu.epamlab.model.impl.*;

import java.util.HashMap;
import java.util.Map;

public class DAOFactory {
    private static Map<Class<?>, Object> map = new HashMap<>();

    static{
		map.put(IUserDAO.class, new UserImplDB());
		map.put(ITaskDAO.class, new TaskImplDB());
		map.put(IFileDAO.class, new FileImplDB());
    }

    public static <T> T getDAO(Class<T> type) {
        return  type.cast(map.get(type));
    }

}
