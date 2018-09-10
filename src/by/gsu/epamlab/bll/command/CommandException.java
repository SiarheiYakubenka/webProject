package by.gsu.epamlab.bll.command;

public class CommandException extends RuntimeException{
    public CommandException() {}
    public CommandException(String message) {
        super(message);
    }
    public CommandException(Throwable cause) {
        super(cause);
    }
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
