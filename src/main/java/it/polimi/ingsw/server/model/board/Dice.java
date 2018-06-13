

package it.polimi.ingsw.server.model.board;


import it.polimi.ingsw.server.exception.ChangeDiceValueException;

public class Dice {
    private Colour colour;
    private int value;
    private static final String[] faces = {

            "\u2680",
            "\u2681",
            "\u2682",
            "\u2683",
            "\u2684",
            "\u2685"
    };
    //constructor of a single dice with a colour and the value 0. If the dice is not launched yet. every dice has always a colour
    public Dice(Colour colour, int value) {
        this.colour = colour;
        this.value = value;
    }

    public void setValue(int value) throws ChangeDiceValueException{
        if(value > 0 && value < 7)
            this.value = value;
        else
            throw new ChangeDiceValueException();
    }

    public int getValue()
    {
        return this.value;
    }

    public void resetValue(){ this.value = 0; }

    public Colour getColour()
    {
        return this.colour;
    }

    public void rollDice() {                 //function used to "to launch " a dice
        value =(int) ((Math.random()*6)+1);
    }

    public void flipDice() {
        value = 7 - value;
    }

    public void incrementValue() throws ChangeDiceValueException {
        if(value == 6)
            throw new ChangeDiceValueException();
        value ++;
    }

    public void decrementValue() throws ChangeDiceValueException {
        if(value == 1)
            throw new ChangeDiceValueException();
        value --;
    }

    @Override
    public String toString() {
        if(this!=null) {
            String escape = this.colour.escape();
            return escape + faces[value - 1] + Colour.RESET;
        }
        return "";
    }

    public void dump(){System.out.println(this); }
}



