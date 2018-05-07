package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class Tenaglia extends ToolCard{
    private static  String name="Tenaglia a Rotelle";
    private static String description = "Dopo il tuo primo turno scegli immediatamente un altro dado. Salta il tuo secondo turn in questo round";
    private static int card_num = 8;



    public void effect(Player p, Round round){
        if(isMyFirstTurn(p, round))
            round.getBoard().getPlayerList().remove(p);//rifai il turno
    }


    public boolean isMyFirstTurn(Player player, Round round){
            if(player.isMyTurn() && round.getTurnNumber() <= round.getPlayerIndex())
                return true;
            else return false;

    }


}
