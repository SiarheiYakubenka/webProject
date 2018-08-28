package by.gsu.epamlab.command.enums;

import by.gsu.epamlab.command.*;
import by.gsu.epamlab.command.ifaces.ActionCommand;

public enum TaskCommandEnum {
    ADD {
        {
            this.command = new AddCommand();
        }
    },
    UPDATE {
        {
            this.command = new UpdateCommand();
        }
    },
    FIX {
        {
            this.command = new FixCommand();
        }
    },
    DELETE{
        {
            this.command = new DelCommand();
        }
    },
    REMOVE{
        {
            this.command = new RemoveCommand();
        }
    },
    RECOVER {
        {
            this.command = new RecoverCommand();
        }
    },
    UPLOAD {
        {
            this.command = new UploadCommand();
        }
    }
    ;
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
