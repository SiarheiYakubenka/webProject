package by.gsu.epamlab.command.enums;

import by.gsu.epamlab.controllers.Constants;
import by.gsu.epamlab.controllers.SessionRequestContent;
import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.factories.DAOFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public enum TaskKindEnum {
    TODAY {
        @Override
        public List<Task> getList(SessionRequestContent content) {
            return getList(content, Constants.TODAY);
        }
    },
    TOMORROW {
        @Override
        public List<Task> getList(SessionRequestContent content) {
            return getList(content, Constants.TOMORROW);
        }
    },
    SOMEDAY{
        @Override
        public List<Task> getList(SessionRequestContent content) {
            return getList(content, Constants.SOMEDAY);
        }
    },
    FIXED {
        @Override
        public List<Task> getList(SessionRequestContent content) {
            return getList(content, Constants.FIXED);
        }
    },
    TRASH {
        @Override
        public List<Task> getList(SessionRequestContent content) {
            return getList(content, Constants.TRASH);
        }
    };
    public abstract List<Task> getList(SessionRequestContent content);

    static List<Task> getList(SessionRequestContent content, String condition) {
        User user = (User) content.getSessionAttribute(Constants.KEY_USER);
        String userName = user.getName();
        ITaskDAO taskDAO = DAOFactory.getDAO(ITaskDAO.class);
        return taskDAO.getList(userName, condition);
    }

    public static TaskKindEnum getKind(SessionRequestContent content) {
        String kind = content.getParameter(Constants.KEY_KIND);
        return TaskKindEnum.valueOf(kind.toUpperCase());
    }

}
