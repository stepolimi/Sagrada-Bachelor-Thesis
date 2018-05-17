package it.polimi.ingsw.server.model.cards.toolCards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;

public class Tampone extends ToolCard {
    private String name = "Tampone Diamantato";
    private String description = "Dopo aver scelto un dado, giralo sulla facia opposta. \n " +
            "6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.";
    private int num_card = 10;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void effects(Player p, Round round){

        Dice d = round.getPendingDice();
        if(d == null)
            return;
        round.setPendingDice(null);

        d.setValue(flip_dice(d));

        round.getBoard().getDiceSpace().insertDice(d);
        if(!this.isUsed()){
            p.setFavour(p.getFavour()-2);
            this.setUsed(true);
        } else{
            p.setFavour(p.getFavour()-1);
        }

    }


    public int flip_dice(Dice d){
        int value = d.getValue();
        if(value == 1)
            return 6;
        else if(value == 2)
            return 5;
        else if(value==3)
            return 4;
        else if(value==4)
            return 3;
        else if(value==5)
            return 2;
        else return 1;


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
