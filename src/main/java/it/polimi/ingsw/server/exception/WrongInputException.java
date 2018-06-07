package it.polimi.ingsw.server.exception;


import it.polimi.ingsw.client.view.Colour;

public class WrongInputException extends Exception{
    public WrongInputException()
    { super();
    this.getMessage();
    }

    @Override
    public String getMessage() {
        return Colour.colorString("Parametro non valido",Colour.ANSI_RED);
    }
}
