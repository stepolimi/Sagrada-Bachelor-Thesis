package it.polimi.ingsw.server.model.cards.objective.cards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.COLUMNS_SCHEMA;
import static it.polimi.ingsw.server.costants.Constants.ROWS_SCHEMA;

public class ColumnsObj extends ObjectiveCard {

    private final String name;
    private final int points;

    public ColumnsObj(String name, int points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Calculates the score relative to the column objective for one player and returns it.
     * @param sch is the schema on which the score must be calculated
     * @return the score of the specified schema
     */
    @Override
    public int scoreCard(Schema sch) {
        int score = 0;
        List<Dice> container;

        for (int j = 0; j < COLUMNS_SCHEMA; j++) {
            container = sch.getDicesInColumn(j);
            if (points == 5 && noColourDuplicates(container) && container.size() == ROWS_SCHEMA)
                score += this.points;

            else if (points == 4 && noNumberDuplicates(container) && container.size() == ROWS_SCHEMA)
                score += this.points;
        }

        return score;
    }


    /**
     * Checks if the list of dices specified has duplicate colours.
     * @param container is a list of dices corresponding to a column of the schema
     * @return if the container has duplicate colours
     */
    private boolean noColourDuplicates(List<Dice> container) {
        for (int i = 0; i < container.size() - 1; i++)
            for (int j = i + 1; j < container.size(); j++)
                if (container.get(i).getColour().equals(container.get(j).getColour()))
                    return false;
        return true;
    }

    /**
     * Checks if the list of dices specified has duplicate numbers.
     * @param container is a list of dices corresponding to a column of the schema
     * @return if the container has duplicate numbers
     */
    private boolean noNumberDuplicates(List<Dice> container) {
        for (int i = 0; i < container.size() - 1; i++)
            for (int j = i + 1; j < container.size(); j++)
                if (container.get(i).getValue() == (container.get(j).getValue()))
                    return false;
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
