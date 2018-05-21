package it.polimi.ingsw.server.model.cards.toolCards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;

public class Diluente extends ToolCard {
    private  String  name = "Diluente per Pasta Calda";
    private String description = "Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto.\n" +
            "Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni d piazzamento";
    private int numCard = 11;
    private boolean used= false;

    public boolean effects(Player p, Round round, int x, int y) {
        round.getBoard().getDicebag().insertDice(round.getPendingDice());
        round.setPendingDice(null);
        Dice d= round.getBoard().getDicebag().takeDice();

        if(placeDiceToSchema(x, y, d, p.getSchema(), numCard) == true) {

            p.getSchema().insertDice(x, y, d,11);
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
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
