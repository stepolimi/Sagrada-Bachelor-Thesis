package it.polimi.ingsw.server.model.cards.objective.cards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

public class CoupleSetObj implements ObjectiveCard {
    private final String name;
    private final int a;
    private final int b;

    public CoupleSetObj(String name, int a, int b) {
        this.name = name;
        this.a = a;
        this.b = b;
    }

    /**
     * Calculates the score relative to the couple objective for one player and returns it.
     * @param sch is the schema on which the score must be calculated
     * @return the score of the specified schema
     */
    @Override
    public int scoreCard(Schema sch) {
        int count1 = 0;
        int count2 = 0;

        for (Dice dice : sch.getDices()) {
            if (dice.getValue() == this.a)
                count1++;
            else if (dice.getValue() == this.b)
                count2++;
        }
        if (count1 < count2)
            return 2 * count1;
        return 2 * count2;
    }

    @Override
    public String getName() {
        return this.name;
    }
}