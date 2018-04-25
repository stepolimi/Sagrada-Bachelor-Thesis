package it.polimi.ingsw;

public class Dice {
    private Colour colour;
    private int value;

    public Dice(Colour colour,int value)
    {
        this.colour = colour;
        this.value = value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public Colour getcolour()
    {
        return this.colour;
    }

    public void setColour(Colour colour)
    {
        this.colour = colour;
    }

    @Override
    public String toString() {
        String escape = this.colour.escape();
        return escape+"["+value+"]" + Colour.RESET;
    }
}

