package it.polimi.ingsw.server.model.cards.toolCards;

import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;

public class Martelletto extends ToolCard  {
    private String name = "Martelletto";
    private String description= "Tira nuovamente tutti i dadi della riserva. \n" +
            "Questa carta può essere utilizzata solo durante il tuo secondo turno, prima di scegliere il secondo dado";
    private int numCard = 7;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean effects(Player p, Round round) {

        if(isMySecondTurn(p, round) && round.getPendingDice() == null){
            for(int i = 0; i < round.getBoard().getDiceSpace().getListDice().size(); i++)
                round.getBoard().getDiceSpace().getListDice().get(i).rollDice();
            if(!this.isUsed()){
                p.setFavour(p.getFavour()-2);
                this.setUsed(true);
            } else{
                p.setFavour(p.getFavour()-1);
            }
            return  true;
        }
        else return false;



    }

    public boolean isMySecondTurn(Player player, Round round){
        if(player.isMyTurn() && round.getTurnNumber() > round.getPlayerIndex())
            return true;
        else return false;
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
