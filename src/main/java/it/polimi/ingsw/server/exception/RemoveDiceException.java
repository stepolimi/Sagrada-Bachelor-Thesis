package it.polimi.ingsw.server.exception;

public class RemoveDiceException extends Exception {
    public RemoveDiceException() {
        super();
        this.getMessage();
    }

    @Override
    public String getMessage() {
        return "Impossible to take this dice";
    }
}
