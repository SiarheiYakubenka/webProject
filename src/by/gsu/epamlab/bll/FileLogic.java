package by.gsu.epamlab.bll;

import by.gsu.epamlab.controllers.Constants;
import by.gsu.epamlab.controllers.SessionRequestContent;
import by.gsu.epamlab.ifaces.IFileDAO;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.factories.DAOFactory;

import java.io.File;
import java.util.logging.Logger;

public final class FileLogic {
    private static final Logger LOGGER = Logger.getLogger(FileLogic.class.getName());
    public static boolean delete(SessionRequestContent content, String root){
        String userName = ((User)content.getSessionAttribute(ConstantsBLL.KEY_USER)).getName();
        String fileName = content.getParameter(ConstantsBLL.KEY_FILENAME);
        File path = new File(root + ConstantsBLL.UPLOADS + userName  +
                Constants.NAME_PROJECT_ROOT + fileName);
        LOGGER.info(path.getAbsolutePath());
        boolean success = false;
        if (path.exists()) {
            success = path.delete();
        } else {
            throw new IllegalArgumentException(ConstantsBLL.FILE_ERROR_EXIST);
        }
        if (success) {
            IFileDAO fileDAO = DAOFactory.getDAO(IFileDAO.class);
            String strIdTask = content.getParameter(ConstantsBLL.KEY_ID);
            fileDAO.delete(Integer.parseInt(strIdTask));
        }
        return success;
    }
}
