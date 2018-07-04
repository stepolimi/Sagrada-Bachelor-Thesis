/*
it represent in the model a single cell of every schema (every schema has 4*5= 20 boxes)
 */


package it.polimi.ingsw.server.model.board;


/**
 * Is a box of a schema, it has the colour and the number restriction and the possible dice.
 */
public class Box {
    private final Colour c;
    private final int number;
    private Dice dice;

    public Box(Colour c, int number) {
        this.c = c;
        this.number = number;
        this.dice = null;
    }

    public int getNumber() {
        return number;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Dice getDice() {
        return dice;
    }

    public Colour getC() {
        return c;
    }
}
