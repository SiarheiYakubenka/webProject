package by.gsu.epamlab.ifaces;

import by.gsu.epamlab.controllers.Constants;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

public abstract class BaseController  extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
        Locale.setDefault(new Locale("en", "US"));
    }

    // to forward to a view layer
    protected void forward(String url, HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    // to forward back with an error message
    protected void forwardError(String url, String message, HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        // to put a message into the request scope
        request.setAttribute(Constants.KEY_ERROR_MESSAGE, message);
        forward(url, request, response);
    }


    protected void forwardJSON(HttpServletResponse response, String json) throws IOException {
        response.setContentType(Constants.TYPE_JSON);
        response.setCharacterEncoding(Constants.UTF_8);
        response.getWriter().write(json);
    }
}
