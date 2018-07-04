package it.polimi.ingsw.client.exceptions;


import it.polimi.ingsw.client.view.Colour;

import static it.polimi.ingsw.client.constants.printCostants.INVALID_PARAMETER;

public class WrongInputException extends Exception {
    public WrongInputException() {
        super();
        this.getMessage();
    }

    @Override
    public String getMessage() {
        return Colour.colorString(INVALID_PARAMETER, Colour.ANSI_RED);
    }
}
