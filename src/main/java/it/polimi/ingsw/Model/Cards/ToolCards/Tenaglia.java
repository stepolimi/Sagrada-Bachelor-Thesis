package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class Tenaglia extends ToolCard{
    private String name="Tenaglia a Rotelle";
    private String description = "Dopo il tuo primo turno scegli immediatamente un altro dado. Salta il tuo secondo turn in questo round";
    private int num_card = 8;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }



    public void effect(Player p, Round round){
        if(isMyFirstTurn(p, round))
            round.getBoard().getPlayerList().remove(p);
        if(!this.isUsed()){
            p.setFavour(p.getFavour()-2);
            this.setUsed(true);
        } else{
            p.setFavour(p.getFavour()-1);
        }//rifai il turno
    }


    public boolean isMyFirstTurn(Player player, Round round){
            if(player.isMyTurn() && round.getTurnNumber() <= round.getPlayerIndex()) {

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
