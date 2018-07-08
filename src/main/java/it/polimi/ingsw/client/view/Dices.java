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

     Dices(String constraint,int number,Colour colour)
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

    /**
     * @return constraint of  dice in cell
     */
    public String getConstraint() {
        return constraint;
    }

    /**
     * @param constraint is constraint of dice in cell
     */
    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    /**
     * get colour of dice
     * @return colour of dice
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * set colour of dice
     * @param colour colour of dice
     */
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    /**
     * set value of dice
     * @param number set value of dice
     */
    public void setNumber(int number){ this.number = number; }

    /**
     * @return value of dice
     */
    public int getNumber() {return number; }
}
