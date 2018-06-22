package it.polimi.ingsw.server.model.cards.objectiveCards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

public class CoupleSetObj extends ObjectiveCard {
    private String name;
    private String description;
    private int a;
    private int b;

    public CoupleSetObj(String name, String description, int a, int b) {
        this.name = name;
        this.description = description;
        this.a = a;
        this.b = b;
    }

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
        if (count1 < count2) {
            System.out.println("couple set objective score: " + 2 * count1);
            return 2 * count1;
        }
        System.out.println("couple set objective score: " + 2 * count2);
        return 2 * count2;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src = src + "|" + this.name + "\n" + "|" + this.description + "\n" + "|" + "setOf: " + this.a + this.b + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump() {
        System.out.println(this);
    }

}