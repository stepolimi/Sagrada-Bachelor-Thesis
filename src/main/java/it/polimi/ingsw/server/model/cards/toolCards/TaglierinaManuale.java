package it.polimi.ingsw.server.model.cards.toolCards;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.RoundTrack;
import it.polimi.ingsw.server.model.game.states.Round;

public class TaglierinaManuale extends ToolCard {

    private  String name= "Taglierina Manuale";
    private String description = "Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato di Round. \n " +
            "Divi rispettare tutte le restrizioni di piazzamento";
   private  int numCard = 12;
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

       if(placeDiceToSchema(rows, columns, d1, p.getSchema(), numCard)  && d1.getcolour() ==
               OneColourRoundTrack(round.getBoard().getRoundTrack(),  i)) {

           p.getSchema().removeDice(x, y);
           try {
               p.getSchema().insertDice(rows, columns, d1,12);
           } catch (InsertDiceException e) {
               e.printStackTrace();
           }
           if (num_insertion == 2) {
               Dice d2 = pickDiceFromSchema(x2, y2, p.getSchema());
               if (!placeDiceToSchema(rows2, columns2, d2, p.getSchema(), numCard))
                   return false;
               if (d1.getcolour() == OneColourRoundTrack(round.getBoard().getRoundTrack(), i)) {
                   p.getSchema().removeDice(x2, y2);
                   try {
                       p.getSchema().insertDice(rows2, columns2, d2,12);
                   } catch (InsertDiceException e) {
                       e.printStackTrace();
                   }
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



    public Colour OneColourRoundTrack(RoundTrack roundTrack, int i) {
        boolean flag = false;
        if (roundTrack.getListRounds(i).size() == 1) {
            return roundTrack.getDice(i, 0).getcolour();

        } else return null;

    }

    public Integer getNum() { return numCard; }

    @Override
    public String toString(){
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src= src + "|" +  this.name.toString() + "\n" + "|" + this.description + "\n" + "|" + "points: " + this.numCard + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump(){
        System.out.println(this);
    }



}

