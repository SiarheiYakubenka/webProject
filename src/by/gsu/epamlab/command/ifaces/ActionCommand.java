package by.gsu.epamlab.command.ifaces;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ActionCommand {
    String execute(HttpServletRequest request) throws IOException, ServletException;
}
