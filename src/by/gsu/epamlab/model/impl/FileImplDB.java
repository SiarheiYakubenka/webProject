package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.ifaces.IFileDAO;

public class FileImplDB extends BaseImplDB implements IFileDAO {
    @Override
    public void upload(String fileName, int idTask) {
        final String SQL = "update tasks set fileName=\"" + fileName + "\" where id=?";
        proccesTask(SQL, idTask);
    }

    @Override
    public void delete(int idTask) {
        proccesTask(ConstantsSQL.DELETE_FILE, idTask);
    }


}
