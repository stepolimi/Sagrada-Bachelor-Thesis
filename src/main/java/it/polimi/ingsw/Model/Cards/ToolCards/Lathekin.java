package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class Lathekin extends ToolCard{
    private String name = "Lathekin";
    private String description = "Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento";
    private int num_card = 4;



    public boolean effects(Player p, Round round, int x, int y, int rows,
                        int columns, int x2, int y2, int rows2, int columns2) {

    boolean flag = true;

        Dice d1 = pickDiceFromSchema(x, y, p.getSchema());

        if(!placeDiceToSchema(rows, columns, d1, p.getSchema(), num_card)) {
            flag = false;
            return false;
        }

        Dice d2 = pickDiceFromSchema(x2, y2, p.getSchema());

        if(!placeDiceToSchema(rows2, columns2, d2, p.getSchema(), num_card)) {
            flag= false;
            return false;
        }

        if(flag){
            p.getSchema().removeDice(x, y);
            p.getSchema().insertDice(rows, columns, d1);
            p.getSchema().removeDice(x2, y2);
            p.getSchema().insertDice(rows2, columns2 , d2);
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
