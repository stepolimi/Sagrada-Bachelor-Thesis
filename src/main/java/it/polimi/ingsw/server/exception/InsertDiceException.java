package it.polimi.ingsw.server.exception;

public class InsertDiceException extends Exception {
    public InsertDiceException() {
        super();
        this.getMessage();
    }

    @Override
    public String getMessage() {
        return "Impossible to insert this dice here";
    }
}
