package it.polimi.ingsw.server.exception;

public class UseToolException extends Exception {
    public UseToolException() {
        super();
        this.getMessage();
    }

    @Override
    public String getMessage() {
        return "Impossible to use this toolcard now";
    }
}
