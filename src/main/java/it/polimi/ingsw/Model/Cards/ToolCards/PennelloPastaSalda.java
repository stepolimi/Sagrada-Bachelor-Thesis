package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class PennelloPastaSalda extends ToolCard {
    private static String name = "Pennello per Pasta Salda";
    private static String description = "Dopo aver scelto un dado. tira nuovamente quel dado. \n" +
            "Se non puoi piazzarlo, riponilo nella riserva";
    private static int num_card = 6;

    //INSERT an expeption in round state which, if Pinza is used, jump the InsertDiceState because already done
    //it doesn'work because is missing the way in order to control the insertion or no of dice in the schema
    public void effects(Player p, Round round, int x, int y){

        Dice d = round.getPendingDice();
        round.setPendingDice(null);
        d.rollDice();
        if(placeDiceToSchema(x, y, d, p.getSchema(), num_card))
            p.getSchema().insertDice(x, y, d);
        else round.getBoard().getDiceSpace().insertDice(d);


    }


}
