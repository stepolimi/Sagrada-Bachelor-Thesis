package it.polimi.ingsw.server.model.cards.objCards;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.Arrays;

import static it.polimi.ingsw.server.serverCostants.Constants.MAX_SCHEMA_DICES;

public class SetObj extends ObjectiveCard {

    private String name;
    private String description;
    private int points;

    public SetObj(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }


    @Override
    public int scoreCard(Schema sch) {
        int[] count;
        int min = MAX_SCHEMA_DICES;

        count = new int[points + 1];
        for (Dice dice : sch.getDices()) {
            if (points == 4)
                colourCounter(count, dice);

            else if (points == 5)
                valueCounter(count, dice);

            else
                return 0;
        }

        System.out.println(Arrays.toString(count));
        for (int i : count)
            if (i < min)
                min = i;
        System.out.println("set objective score: " + points * min);
        return points * min;
    }

    private void colourCounter(int[] count, Dice dice) {
        if (dice.getColour() == Colour.ANSI_RED)
            count[0]++;
        else if (dice.getColour() == Colour.ANSI_BLUE)
            count[1]++;
        else if (dice.getColour() == Colour.ANSI_GREEN)
            count[2]++;
        else if (dice.getColour() == Colour.ANSI_PURPLE)
            count[3]++;
        else if (dice.getColour() == Colour.ANSI_YELLOW)
            count[4]++;
    }

    private void valueCounter(int[] count, Dice dice) {
        if (dice.getValue() == 1)
            count[0]++;
        else if (dice.getValue() == 2)
            count[1]++;
        else if (dice.getValue() == 3)
            count[2]++;
        else if (dice.getValue() == 4)
            count[3]++;
        else if (dice.getValue() == 5)
            count[4]++;
        else if (dice.getValue() == 6)
            count[5]++;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src = src + "|" + this.name.toString() + "\n" + "|" + this.description + "\n" + "|" + "points: " + this.points + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump() {
        System.out.println(this);
    }

}
