package it.polimi.ingsw.client.view;

public class Dices {
    private String costraint;
    private int number;
    private Colour colour;

    public static final String[] faces = {

            "\u2680",
            "\u2681",
            "\u2682",
            "\u2683",
            "\u2684",
            "\u2685"
    };

    public Dices(String costraint,int number,Colour colour)
    {
        this.colour = colour;
        this.costraint = costraint;
        this.number = number;
    }

    @Override
    public String toString() {
        String str="";
        if(colour==null)
        {
            try {
                 Integer.parseInt(costraint);
                str += "[" + costraint + "]";
            }catch(NumberFormatException e)
            {
                str+= costraint+"[ ]"+Colour.RESET;
            }
        }else
        {
            String escape = this.colour.escape();
            return "["+escape + faces[number - 1] + Colour.RESET+"]";
        }
        return str;
    }

    public String getCostraint() {
        return costraint;
    }

    public void setCostraint(String costraint) {
        this.costraint = costraint;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setNumber(int number){ this.number = number;
    }
}
