package it.polimi.ingsw.Model.Cards.ToolCards;


import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;

public class Alesatore extends ToolCard {
    private static String name = "Alesatore per lamina di rame";
    private static String description = "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore. \nDecvi rispettare tutte le altre " +
            "restrizioni di piazzamento";
    private static int num_card = 3;



    public boolean effects(Player p, int x, int y, int rows, int columns) {


        Dice d = pickDiceFromSchema(x, y, p.getSchema());

        if(placeDiceToSchema(rows, columns, d, p.getSchema(), 3) == true) {
            p.getSchema().removeDice(x, y);
            p.getSchema().insertDice(rows, columns, d);
            return true;
        }
        else return false;

    }







}
