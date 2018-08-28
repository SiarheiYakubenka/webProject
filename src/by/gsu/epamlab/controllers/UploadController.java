package by.gsu.epamlab.controllers;

import by.gsu.epamlab.ifaces.IFileDAO;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.factories.DAOFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UploadController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(UploadController.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajaxUpdateResult = "";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.KEY_USER);
        String strId = null;
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

            for (FileItem item : items) {

                if (item.isFormField()) {
                    strId = item.getString();
                }else {
                    String fileName = item.getName();
                    String root = getServletContext().getRealPath(Constants.NAME_PROJECT_ROOT);
                    File path = new File(root + Constants.UPLOADS + user.getName());
                    boolean success = true;
                    if (!path.exists()) {
                        success =  path.mkdirs();
                    }
                    if (success) {
                        File uploadedFile = new File(path + Constants.NAME_PROJECT_ROOT + fileName);
                        if (uploadedFile.exists()) {
                            fileName = Constants.NEW_NAME + fileName;
                            uploadedFile = new File( path + Constants.NAME_PROJECT_ROOT  + fileName);
                        }
                        LOGGER.info(uploadedFile.getAbsolutePath());
                        item.write(uploadedFile);
                        IFileDAO fileDAO = DAOFactory.getDAO(IFileDAO.class);
                        fileDAO.upload(user.getName(), fileName, Integer.parseInt(strId));

                        response.setContentType("text/plain");
                        response.setCharacterEncoding("UTF-8");

                        ajaxUpdateResult = "File \"" + fileName + "\" is successfully uploaded\n\r";
                    } else {
                        ajaxUpdateResult = "Error. No rights to write\n\r";
                    }


                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            ajaxUpdateResult = "Error. File was not upload\n\r";
        }
        response.getWriter().print(ajaxUpdateResult);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
