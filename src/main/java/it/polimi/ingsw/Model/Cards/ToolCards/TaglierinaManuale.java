package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.RoundTrack;
import it.polimi.ingsw.Model.game.states.Round;

public class TaglierinaManuale extends ToolCard {

    private static  String name= "Taglierina Manuale";
    private static String description = "Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato di Round. \n " +
            "Divi rispettare tutte le restrizioni di piazzamento";
   private static int num_card = 12;

   public boolean effects(Player p, Round round, int x, int y, int rows,
                       int columns, int x2, int y2, int rows2, int columns2, int num_insertion, int i){




       Dice d1 = pickDiceFromSchema(x, y, p.getSchema());

       if(placeDiceToSchema(rows, columns, d1, p.getSchema(), num_card)  && d1.getcolour() ==
               OneColourRoundTrack(round.getBoard().getRoundTrack(),  i)) {

           p.getSchema().removeDice(x, y);
           p.getSchema().insertDice(rows, columns, d1);
           if(num_insertion == 2) {
               Dice d2 = pickDiceFromSchema(x2, y2, p.getSchema());
            if(!placeDiceToSchema(rows2, columns2, d2, p.getSchema(), num_card))
                return false;
               if (d1.getcolour() == OneColourRoundTrack(round.getBoard().getRoundTrack(), i)) {
                   p.getSchema().removeDice(x2, y2);
                   p.getSchema().insertDice(rows2, columns2, d2);
               }
               return true;
           }
           else return true;
       }

       return false;



   }



    public Colour OneColourRoundTrack(RoundTrack roundTrack, int i){
       boolean flag = false;
       if(roundTrack.getListRounds(i).size() == 1) {
           return roundTrack.getDice(i, 0).getcolour();

       }
       else return null;

}


}

