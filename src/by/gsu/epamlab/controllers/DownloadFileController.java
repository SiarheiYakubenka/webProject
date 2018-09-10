package by.gsu.epamlab.controllers;

import by.gsu.epamlab.bll.ConstantsBLL;
import by.gsu.epamlab.model.beans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;

public class DownloadFileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(DownloadFileController.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SessionRequestContent content = new SessionRequestContent(request);
        String root = getServletContext().getRealPath(Constants.NAME_PROJECT_ROOT);
        String userName = ((User)content.getSessionAttribute(Constants.KEY_USER)).getName();

        String fileName = content.getParameter(Constants.KEY_FILENAME);
        String localFile = root + ConstantsBLL.UPLOADS + userName  + Constants.NAME_PROJECT_ROOT + fileName;
        doDownload(response, localFile, fileName);
    }

    private void doDownload(HttpServletResponse response, String localFile, String fileToWrite)
            throws IOException {
        LOGGER.info(String.format(Constants.MESSAGE_DOWNLOAD, localFile, fileToWrite));

        File inputFile = new File(localFile);
        String mimetype = getServletContext().getMimeType(fileToWrite);

        response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
        response.setContentLength((int) inputFile.length());
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToWrite + "\"");



        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile));
        BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {

            byte[] buffer = new byte[8192];
            for (int length = 0; (length = input.read(buffer)) > 0;) {
                output.write(buffer, 0, length);
            }
        }
    }
}
