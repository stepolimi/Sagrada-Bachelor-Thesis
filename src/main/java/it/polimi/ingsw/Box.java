/*
it represent in the model a single cell of every schema (every schema has 4*5= 20 boxes)
 */


package it.polimi.ingsw;



public class Box {
    private Colour c;   //colour of box. null if box is white
    private int number;  //nuber of box. 0 if there's no number
    private Dice dice;   //dice placed on box. null if it's empty
    private boolean full;   //return if there's / not a dice

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
        this.setFull(true);
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

    @Override
    public String toString(){     //method used to print every scema. now this is situated in a class of Model part
                                    //to MOVE in the view part
       // String string = "Box: " + "number restriction= " + this.number + ", colour restriction= " +this.c + "\n" + "Dice: ";
        if(this.full == true)
            return "[ "+this.dice.toString()+" ]";
        else if(this.getNumber()!=0)
        {
            return "[ "+this.getNumber()+" ]";
        }else if(this.getC()!=null){
            return this.getC().escape()+"[   ]"+Colour.RESET;
        }else
            return "[   ]";

    }
    public void dump(){System.out.println(this); }
}
