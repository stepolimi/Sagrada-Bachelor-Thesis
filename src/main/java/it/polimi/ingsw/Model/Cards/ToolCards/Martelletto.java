package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.DiceSpace;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class Martelletto extends ToolCard  {
    private static String name = "Martelletto";
    private static String description= "Tira nuovamente tutti i dadi della riserva. \n" +
            "Questa carta può essere utilizzata solo durante il tuo secondo turno, prima di scegliere il secondo dado";
    private static  int num_card = 7;
    public boolean effects(Player p, Round round, DiceSpace diceSpace) {

        if(isMySecondTurn(p, round) && round.getPendingDice() == null){
            for(int i = 0; i < diceSpace.getListDice().size(); i++)
                diceSpace.getListDice().get(i).rollDice();
            return  true;
        }
        else return false;



    }

    public boolean isMySecondTurn(Player player, Round round){
        if(player.isMyTurn() && round.getTurnNumber() > round.getPlayerIndex())
            return true;
        else return false;
    }


}
