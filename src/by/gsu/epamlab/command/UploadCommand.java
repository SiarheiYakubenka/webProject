package by.gsu.epamlab.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadCommand implements by.gsu.epamlab.command.ifaces.ActionCommand {
    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String success = "true";
        Part part = request.getPart("file1");
        part.getSubmittedFileName();

        File file = new File("D:/uploadedFiles",
                part.getSubmittedFileName());


        try (InputStream is = part.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            byte[] buffer = new byte[4096];

            int count = is.read(buffer);

            while (count != -1) {
                fileOutputStream.write(buffer, 0, count);
                count = is.read(buffer);
            }
        } catch (IOException e) {
            file.delete();
            success = "false";
        }

        return success;
    }
}
