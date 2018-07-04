package it.polimi.ingsw.server.model.cards.objective.cards;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import static it.polimi.ingsw.server.costants.Constants.MAX_SCHEMA_DICES;

public class SetObj implements ObjectiveCard {

    private final String name;
    private final int points;

    public SetObj(String name, int points) {
        this.name = name;
        this.points = points;
    }


    /**
     * Calculates the score relative to the set objective for one player and returns it.
     * @param sch is the schema on which the score must be calculated
     * @return the score of the specified schema
     */
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
        for (int i : count)
            if (i < min)
                min = i;
        return points * min;
    }

    /**
     * Checks the colour of the dice and increments the relative counter.
     * @param count is the counter for each possible colour of a dice
     * @param dice is the dice to check
     */
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

    /**
     * Checks the value of the dice and increments the relative counter.
     * @param count is the counter for each possible value of a dice
     * @param dice is the dice to check
     */
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
}
