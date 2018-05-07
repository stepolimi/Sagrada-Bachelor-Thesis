package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;

public class TaglierinaCircolare extends ToolCard {
    private String name = "Taglierina Circolare";
    private String description = "Dopo aver scelto un dado, scambia quel dado con un dado sul tracciato di round";
    private int num_card = 5;

    public void effects(Player p, Round round, int indexRound, int indexDice){


        Dice d = round.getPendingDice();
        round.setPendingDice(null);
        d = switchDice(indexRound, indexDice, d, round);
        round.getBoard().getDiceSpace().insertDice(d);
    }

    public Dice switchDice(int roundIndex, int indexDice, Dice d, Round round){
        Dice trasport = round.getBoard().getRoundTrack().removeDice(roundIndex, indexDice );
        round.getBoard().getRoundTrack().insertDice(d, roundIndex );
        return trasport;
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
