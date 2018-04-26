package it.polimi.ingsw;

public class Box {
    private Colour c;
    private int number;
    private Dice dice;
    private boolean full;

    public Box(Colour c,int number)
    {
        this.c = c;
        this.number = number;
        this.dice = null;
        this.full = false;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Dice getDice() {
        return dice;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public boolean isOccupied() {
        return full;
    }


    public Colour getC() {
        return c;
    }

    public void setC(Colour c) {
        this.c = c;
    }
}