package it.polimi.ingsw.server.model.cards.objectiveCards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

import static it.polimi.ingsw.server.serverCostants.Constants.COLUMNS_SCHEMA;
import static it.polimi.ingsw.server.serverCostants.Constants.ROWS_SCHEMA;

public class ColumnsObj extends ObjectiveCard {

    private String name;
    private String description;
    private int points;

    public ColumnsObj(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

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

        System.out.println("column objective score: " + score);
        return score;
    }


    private boolean noColourDuplicates(List<Dice> container) {
        for (int i = 0; i < container.size() - 1; i++)
            for (int j = i + 1; j < container.size(); j++)
                if (container.get(i).getColour().equals(container.get(j).getColour()))
                    return false;
        return true;
    }

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

    @Override
    public String toString() {
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src = src + "|" + this.name + "\n" + "|" + this.description + "\n" + "|" + "points: " + this.points + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump() {
        System.out.println(this);
    }

}