package it.polimi.ingsw.server.exception;

public class ChangeDiceValueException extends Exception {
    public ChangeDiceValueException() {
        super();
        this.getMessage();
    }

    @Override
    public String getMessage() {
        return "Impossible to change the dice's value to this";
    }
}
