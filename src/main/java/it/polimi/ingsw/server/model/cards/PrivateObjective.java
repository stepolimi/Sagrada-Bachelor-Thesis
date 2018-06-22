package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Schema;

public class PrivateObjective {
    private String name;
    private String description;
    private Colour c;

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

    public String getColour() {
        if (c == Colour.ANSI_GREEN) {
            return "verde";
        } else if (c == Colour.ANSI_BLUE) {
            return "blu";
        } else if (c == Colour.ANSI_RED) {
            return "rosso";
        } else if (c == Colour.ANSI_PURPLE) {
            return "viola";
        } else if (c == Colour.ANSI_YELLOW) {
            return "giallo";
        }
        return "";
    }

    @Override
    public String toString() {
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src = src + "|" + this.name.toString() + "\n" + "|" + this.description + "\n" + "|" + "points: " + this.c + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump() {
        System.out.println(this);
    }

}
