package it.polimi.ingsw.server.model.cards.toolCards;


import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;

public class Alesatore extends ToolCard {
    private String name = "Alesatore per lamina di rame";
    private String description = "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore. \nDevi rispettare tutte le altre " +
            "restrizioni di piazzamento";
    private int numCard = 3;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }



    public boolean effects(Player p, int x, int y, int rows, int columns) {


        Dice d = pickDiceFromSchema(x, y, p.getSchema());

        if(placeDiceToSchema(rows, columns, d, p.getSchema(), 3) == true) {
            p.getSchema().removeDice(x, y);
            p.getSchema().insertDice(rows, columns, d,3);
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





