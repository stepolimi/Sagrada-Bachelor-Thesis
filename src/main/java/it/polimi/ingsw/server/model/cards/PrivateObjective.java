package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Schema;

import static it.polimi.ingsw.server.costants.Constants.*;

public class PrivateObjective {
    private String name;
    private String description;
    private Colour c;

    /**
     * Calculates the score relative to the private objective for one player and returns it.
     * @param sch is the schema on which the score must be calculated
     * @return the score of the specified schema
     */
    public int scoreCard(Schema sch) {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (sch.getTable(i, j).getDice() != null)
                    if (sch.getTable(i, j).getDice().getColour().equals(this.c))
                        score += sch.getTable(i, j).getDice().getValue();
            }
        }
        return score;
    }

    /**
     * @return the colour of the private object in String.
     */
    public String getColour() {
        if (c == Colour.ANSI_GREEN) {
            return GREEN;
        } else if (c == Colour.ANSI_BLUE) {
            return BLUE;
        } else if (c == Colour.ANSI_RED) {
            return RED;
        } else if (c == Colour.ANSI_PURPLE) {
            return PURPLE;
        } else if (c == Colour.ANSI_YELLOW) {
            return YELLOW;
        }
        return "";
    }
}
