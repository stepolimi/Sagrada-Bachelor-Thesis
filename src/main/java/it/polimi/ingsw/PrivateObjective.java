//class of PrivateObjective cards with description and colour attributes.
//it will be used a method that, depending on the colour, calcolates the score given by the private objective
//for each player



package it.polimi.ingsw;

public class PrivateObjective {
    private String description;
    private Colour colour;


    public PrivateObjective(String description, Colour c){
        this.description = description;
        this.colour = c;
    }

}
