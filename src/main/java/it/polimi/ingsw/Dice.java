

package it.polimi.ingsw;



public class Dice {
    private Colour colour;
    private int value;
    public static final String[] faces = {

            "\u2680",
            "\u2681",
            "\u2682",
            "\u2683",
            "\u2684",
            "\u2685"
    };

    public Dice(Colour colour, int value) //constructor of a single dice wit a colour and the value 0 if the dice is
                                            //not launched yet. every dice has ALWAYS a colour
    {
        this.colour = colour;
        this.value = value;
    }

    // setta il valore del dado
    public void setValue(int value)
    {
        this.value = value;
    }           //used to set value after a dice launch
    // restituisce il valore del dado
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
        return escape +  faces[value-1]  + Colour.RESET;

    }
    public void rollDice()                  //function used to "to launch " a dice
    {
        int numero =(int) ((Math.random()*6)+1);
        System.out.println(numero);
        this.setValue(numero);
    }
    public void dump(){System.out.println(this); }
}



