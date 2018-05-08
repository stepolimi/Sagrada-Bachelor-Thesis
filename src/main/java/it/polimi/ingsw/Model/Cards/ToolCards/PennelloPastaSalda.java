package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class PennelloPastaSalda extends ToolCard {
    private String name = "Pennello per Pasta Salda";
    private String description = "Dopo aver scelto un dado. tira nuovamente quel dado. \n" +
            "Se non puoi piazzarlo, riponilo nella riserva";
    private int num_card = 6;
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
        if(placeDiceToSchema(x, y, d, p.getSchema(), num_card))
            p.getSchema().insertDice(x, y, d);
        else round.getBoard().getDiceSpace().insertDice(d);
        if(!this.isUsed()){
            p.setFavour(p.getFavour()-2);
            this.setUsed(true);
        } else{
            p.setFavour(p.getFavour()-1);
        }


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
