package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exception.ChangeDiceValueException;

import java.util.Random;

public class Dice {
    private final Colour colour;
    private int value;

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
    void resetValue(){ this.value = 0; }

    public Colour getColour()
    {
        return this.colour;
    }

    /**
     * Changes randomly the value of the dice.
     */
    public void rollDice() {                 //function used to "to launch " a dice
        Random random = new Random();
        value = random.nextInt(6) + 1;
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
     * Decrements the dice's value by one. If it's actual value is one, throws an exception.
     * @throws ChangeDiceValueException if the actual value of the dice is one.
     */
    public void decrementValue() throws ChangeDiceValueException {
        if(value == 1)
            throw new ChangeDiceValueException();
        value --;
    }
}



