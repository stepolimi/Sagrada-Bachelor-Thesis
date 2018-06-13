package it.polimi.ingsw.server.model.cards.objCards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

public class DiagonalObj extends ObjectiveCard {

    private String name;
    private String description;
    private int[] diagonals = new int[4];

    public DiagonalObj(String name, String description) {
        this.name = name;
        this.description = description;
        diagonals[0] = 0;
        diagonals[1] = 2;
        diagonals[2] = 5;
        diagonals[3] = 7;
    }

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
        System.out.println("diagonal objective score: " + score);
        return score;
    }

    private int checkNearDices(List<Dice> dices, Dice dice) {
        for (int index : diagonals)
            if (dices.get(index) != null)
                if (dices.get(index).getColour() == dice.getColour()) {
                    return 1;
                }
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src = src + "|" + this.name.toString() + "\n" + "|" + this.description + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump() {
        System.out.println(this);
    }
}