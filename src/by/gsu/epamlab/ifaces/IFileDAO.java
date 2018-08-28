package by.gsu.epamlab.ifaces;

public interface IFileDAO {
    void upload(String userName, String fileName, int idTask);
    void delete(String userName, String fileName, int idTask);
}
