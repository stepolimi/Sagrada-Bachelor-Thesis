

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

    public Dice(Colour colour, int value) {
        this.colour = colour;
        this.value = value;
    }

    /**
     * Sets the specified value to the dice. If it is not valid, throws an exception.
     * @param value is the value to be set to the dice.
     * @throws ChangeDiceValueException when the value is not valid.
     */
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

    /**
     * Resets the value of the dice to the initial value of zero.
     */
    public void resetValue(){ this.value = 0; }

    public Colour getColour()
    {
        return this.colour;
    }

    /**
     * Changes randomly the value of the dice.
     */
    public void rollDice() {                 //function used to "to launch " a dice
        value =(int) ((Math.random()*6)+1);
    }

    /**
     * Sets the value of the dice to the opposite's face value.
     */
    public void flipDice() {
        value = 7 - value;
    }

    /**
     * Increments the dice's value by one. If it's actual value is six, throws an exception.
     * @throws ChangeDiceValueException if the actual value of the dice is six.
     */
    public void incrementValue() throws ChangeDiceValueException {
        if(value == 6)
            throw new ChangeDiceValueException();
        value ++;
    }

    /**
     * Dacrements the dice's value by one. If it's actual value is one, throws an exception.
     * @throws ChangeDiceValueException if the actual value of the dice is one.
     */
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



