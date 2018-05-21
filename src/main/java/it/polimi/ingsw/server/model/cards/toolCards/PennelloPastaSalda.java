package it.polimi.ingsw.server.model.cards.toolCards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;

public class PennelloPastaSalda extends ToolCard {
    private String name = "Pennello per Pasta Salda";
    private String description = "Dopo aver scelto un dado. tira nuovamente quel dado. \n" +
            "Se non puoi piazzarlo, riponilo nella riserva";
    private int numCard = 6;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    //INSERT an expeption in round state which, if Pinza is used, jump the InsertDiceState because already done
    //it doesn'work because is missing the way in order to control the insertion or no of dice in the schema
    public void effects(Player p, Round round, int x, int y){

        Dice d = round.getPendingDice();

        if(d==null) return;

        round.setPendingDice(null);
        d.rollDice();
        if(placeDiceToSchema(x, y, d, p.getSchema(), numCard))
            p.getSchema().insertDice(x, y, d,6);
        else round.getBoard().getDiceSpace().insertDice(d);
        if(!this.isUsed()){
            p.setFavour(p.getFavour()-2);
            this.setUsed(true);
        } else{
            p.setFavour(p.getFavour()-1);
        }


    }

    public int getNum() { return numCard; }

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
