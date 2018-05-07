package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class Pinza extends ToolCard {
    private String name = "Pinza Sgrossatrice";
    private String description = "Dopo aver scelto un dado,aumenta o diminuisci il valore del dado scleto di 1.\n Non puoi cambaire un 6 in un 1" +
            "o un 1 in un 6";
    private int num_card = 1;


    public boolean effects(Player p, Round round, int change) {


        Dice d = round.getPendingDice();
        round.setPendingDice(null);

        if (((change == 1) || (change == -1)) ){
            if(((d.getValue()== 6 && change == 1) || (d.getValue()==1)&&(change==-1))){
                round.getBoard().getDiceSpace().insertDice(d);
                return false;

            }
            else
                {
                d.setValue(d.getValue() + change);
                round.getBoard().getDiceSpace().insertDice(d);
            return true;
                }
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