package it.polimi.ingsw.server.virtual.view;

public class Dices {
    private String constraint;
    private int number;
    private Colour colour;

    static final String[] faces = {

            "\u2680",
            "\u2681",
            "\u2682",
            "\u2683",
            "\u2684",
            "\u2685"
    };

    Dices() {
        this.colour = null;
        this.constraint = "";
        this.number = 0;
    }

    public String getConstraint() {
        return constraint;
    }

    void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public Colour getColour() { return colour; }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setNumber(int number){ this.number = number; }

    public int getNumber() {return number; }
}
