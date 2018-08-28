package by.gsu.epamlab.filters;

import by.gsu.epamlab.controllers.Constants;
import by.gsu.epamlab.model.beans.Role;
import by.gsu.epamlab.model.beans.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", servletNames = {"AppController", "UploadController"} )
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.KEY_USER);
        if (user == null || user.getRole() == Role.FAKER) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(Constants.JUMP_LOGIN);
            dispatcher.forward(request, response);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
