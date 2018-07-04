package it.polimi.ingsw.server.model.cards.objective.cards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

public class DiagonalObj implements ObjectiveCard {

    private final String name;
    private final int[] diagonals = new int[4];

    public DiagonalObj(String name) {
        this.name = name;
        diagonals[0] = 0;
        diagonals[1] = 2;
        diagonals[2] = 5;
        diagonals[3] = 7;
    }

    /**
     * Calculates the score relative to the diagonal objective for one player and returns it.
     * @param sch is the schema on which the score must be calculated
     * @return the score of the specified schema
     */
    @Override
    public int scoreCard(Schema sch) {
        int score = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (sch.getTable(i, j).getDice() != null) {
                    score += checkNearDices(sch.nearDice(i, j), sch.getTable(i, j).getDice());

                }
            }
        }
        return score;
    }

    /**
     * Checks if there is another dice on the diagonals that has the same colour of the dice specified.
     * @param dices is a list of dices
     * @param dice is a dice
     * @return 1 if there is a dice on the diagonals that has the same colour of the dice specified. Otherwise it returns 0
     */
    private int checkNearDices(List<Dice> dices, Dice dice) {
        for (int index : diagonals)
            if (dices.get(index) != null && (dices.get(index).getColour() == dice.getColour()))
                return 1;
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }
}