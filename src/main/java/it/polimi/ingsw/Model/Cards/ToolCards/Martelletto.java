package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class Martelletto extends ToolCard  {
    private String name = "Martelletto";
    private String description= "Tira nuovamente tutti i dadi della riserva. \n" +
            "Questa carta può essere utilizzata solo durante il tuo secondo turno, prima di scegliere il secondo dado";
    private int num_card = 7;
    public boolean effects(Player p, Round round) {

        if(isMySecondTurn(p, round) && round.getPendingDice() == null){
            for(int i = 0; i < round.getBoard().getDiceSpace().getListDice().size(); i++)
                round.getBoard().getDiceSpace().getListDice().get(i).rollDice();
            return  true;
        }
        else return false;



    }

    public boolean isMySecondTurn(Player player, Round round){
        if(player.isMyTurn() && round.getTurnNumber() > round.getPlayerIndex())
            return true;
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
