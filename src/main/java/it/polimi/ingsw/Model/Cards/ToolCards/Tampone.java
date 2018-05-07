package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class Tampone extends ToolCard {
    private static String name = "Tampone Diamantato";
    private String description = "Dopo aver scelto un dado, giralo sulla facia opposta. \n " +
            "6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.";
    private static int num_card = 10;

    public void effects(Player p, Round round){

        Dice d = round.getPendingDice();
        round.setPendingDice(null);
        d.setValue(flip_dice(d));

        round.getBoard().getDiceSpace().insertDice(d);

    }


    public int flip_dice(Dice d){
        int value = d.getValue();
        if(value == 1)
            return 6;
        else if(value == 2)
            return 5;
        else if(value==3)
            return 4;
        else if(value==4)
            return 3;
        else if(value==5)
            return 2;
        else return 1;


    }

}
