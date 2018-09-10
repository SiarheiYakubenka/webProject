package by.gsu.epamlab.ifaces;

public interface IFileDAO {
    void upload(String fileName, int idTask);
    void delete(int idTask);
}
