package it.polimi.ingsw.server.model.cards.toolCards;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;

public class Lathekin extends ToolCard{
    private String name = "Lathekin";
    private String description = "Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento";
    private int numCard = 4;
    private boolean used= false;


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }


    public boolean effects(Player p, Round round, int x, int y, int rows,
                        int columns, int x2, int y2, int rows2, int columns2) {

    boolean flag = true;

        Dice d1 = pickDiceFromSchema(x, y, p.getSchema());

        if(!placeDiceToSchema(rows, columns, d1, p.getSchema(), numCard)) {
            flag = false;
            return false;
        }

        Dice d2 = pickDiceFromSchema(x2, y2, p.getSchema());

        if(!placeDiceToSchema(rows2, columns2, d2, p.getSchema(), numCard)) {
            flag= false;
            return false;
        }

        if(flag){
            p.getSchema().removeDice(x, y);
            try {
                p.getSchema().insertDice(rows, columns, d1,4);
                p.getSchema().removeDice(x2, y2);
                p.getSchema().insertDice(rows2, columns2 , d2,4);
            } catch (InsertDiceException e) {
                e.printStackTrace();
            }
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
