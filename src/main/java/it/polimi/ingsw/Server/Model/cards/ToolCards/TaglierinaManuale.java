package it.polimi.ingsw.Server.Model.cards.ToolCards;

import it.polimi.ingsw.Server.Model.board.Colour;
import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.board.RoundTrack;
import it.polimi.ingsw.Server.Model.game.states.Round;

public class TaglierinaManuale extends ToolCard {

    private  String name= "Taglierina Manuale";
    private String description = "Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato di Round. \n " +
            "Divi rispettare tutte le restrizioni di piazzamento";
   private  int num_card = 12;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

   public boolean effects(Player p, Round round, int x, int y, int rows,
                       int columns, int x2, int y2, int rows2, int columns2, int num_insertion, int i){




       Dice d1 = pickDiceFromSchema(x, y, p.getSchema());

       if(placeDiceToSchema(rows, columns, d1, p.getSchema(), num_card)  && d1.getcolour() ==
               OneColourRoundTrack(round.getBoard().getRoundTrack(),  i)) {

           p.getSchema().removeDice(x, y);
           p.getSchema().insertDice(rows, columns, d1);
           if (num_insertion == 2) {
               Dice d2 = pickDiceFromSchema(x2, y2, p.getSchema());
               if (!placeDiceToSchema(rows2, columns2, d2, p.getSchema(), num_card))
                   return false;
               if (d1.getcolour() == OneColourRoundTrack(round.getBoard().getRoundTrack(), i)) {
                   p.getSchema().removeDice(x2, y2);
                   p.getSchema().insertDice(rows2, columns2, d2);
               }
               if (!this.isUsed()) {
                   p.setFavour(p.getFavour() - 2);
                   this.setUsed(true);
               } else {
                   p.setFavour(p.getFavour() - 1);
               }
               return true;
           } else {
               if(!this.isUsed()){
                   p.setFavour(p.getFavour()-2);
                   this.setUsed(true);
               } else{
                   p.setFavour(p.getFavour()-1);
               }
               return true;

           }
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
    @Override
    public String toString(){
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src= src + "|" +  this.name.toString() + "\n" + "|" + this.description + "\n" + "|" + "points: " + this.num_card + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump(){
        System.out.println(this);
    }



}
