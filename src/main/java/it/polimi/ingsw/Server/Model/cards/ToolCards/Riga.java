package it.polimi.ingsw.Server.Model.cards.ToolCards;

import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.game.states.Round;

import java.util.List;

public class Riga extends ToolCard {
    private String name = "Riga in sughero";
    private String description = "Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente ad un altro " +
            "dado. \n Deve rispettare tutte le restrizioni di piazzamento";
    private int num_card = 9;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean effects(Player player, Round round, int x, int y){

        boolean found = false;
        Dice d = round.getPendingDice();
        round.setPendingDice(null);
        if(d == null)
            return false;

        if(placeDiceToSchema(x, y, d, player.getSchema(), 9)) {
            List<Dice> list = player.getSchema().nearDice(x, y);
            for (Dice dice: list) {
                if (dice != null)
                    found = true;
            }
            if(!found)
                player.getSchema().insertDice(x, y, d);
            if(!this.isUsed()){
                player.setFavour(player.getFavour()-2);
                this.setUsed(true);
            } else{
                player.setFavour(player.getFavour()-1);
            }
            return true;

            }
        else{
            round.getBoard().getDiceSpace().insertDice(d);
            return false;
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
