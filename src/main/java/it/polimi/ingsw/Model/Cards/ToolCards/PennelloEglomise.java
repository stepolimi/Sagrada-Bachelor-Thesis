package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class PennelloEglomise extends ToolCard {
    private static String name = "Pennello per Eglomise";
    private static String description = "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore. \nDecvi rispettare tutte le altre " +
            "restrizioni di piazzamento";
    private static int num_card = 2;



    public boolean effects(Player p, Round round, int x, int y, int rows,
                        int columns) {


        Dice d = pickDiceFromSchema(x, y, p.getSchema());

        if(placeDiceToSchema(rows, columns, d, p.getSchema(), num_card) == true) {
            p.getSchema().removeDice(x, y);
            p.getSchema().insertDice(rows, columns, d);
            return true;
        }
        else return false;


    }



}
