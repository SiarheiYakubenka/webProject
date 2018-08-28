package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.ifaces.IFileDAO;
import java.util.logging.Logger;

public class FileImplDB extends BaseImplDB implements IFileDAO {
    private static final Logger LOGGER = Logger.getLogger(FileImplDB.class.getName());
    @Override
    public void upload(String userName, String fileName, int idTask) {
        final String SQL = "update " + userName + "_tasks set fileName=\"" + fileName + "\" where id=?";
        proccesTask(SQL, idTask);
    }



    @Override
    public void delete(String userName, String fileName, int idTask) {

    }


}
