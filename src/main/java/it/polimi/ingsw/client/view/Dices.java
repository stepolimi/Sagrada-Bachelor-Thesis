package it.polimi.ingsw.client.view;

public class Dices {
    private String constraint;
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

    public Dices(String constraint,int number,Colour colour)
    {
        this.colour = colour;
        this.constraint = constraint;
        this.number = number;
    }

    @Override
    public String toString() {
        String str="";
        if(colour==null)
        {
            try {
                 Integer.parseInt(constraint);
                str += "[" + constraint + "]";
            }catch(NumberFormatException e)
            {
                str+= constraint+"[ ]"+Colour.RESET;
            }
        }else
        {
            String escape = this.colour.escape();
            return "["+escape + faces[number - 1] + Colour.RESET+"]";
        }
        return str;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setNumber(int number){ this.number = number; }

    public int getNumber() {return number; }
}
