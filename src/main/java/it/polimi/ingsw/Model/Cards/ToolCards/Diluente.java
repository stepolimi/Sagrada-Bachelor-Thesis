package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class Diluente extends ToolCard {


    private  String  name = "Diluente per Pasta Calda";
    private String description = "Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto.\n" +
            "Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni d piazzamento";
    private int num_card = 11;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }




    public boolean effects(Player p, Round round, int x, int y) {

        round.getBoard().getDicebag().insertDice(round.getPendingDice());
        round.setPendingDice(null);
        Dice d= round.getBoard().getDicebag().takeDice();

        if(placeDiceToSchema(x, y, d, p.getSchema(), num_card) == true) {

            p.getSchema().insertDice(x, y, d);
            if(!this.isUsed()){
                p.setFavour(p.getFavour()-2);
                this.setUsed(true);
            } else{
                p.setFavour(p.getFavour()-1);
            }
            return true;
        }
        else return false;


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
