//it's the set of dice given at the beginning of every game. there only the constructor with 18 dices for each colour

package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private List<Dice> dices = new ArrayList<Dice>();
    private static int numDices = 90;

    public Bag() {
        for (int i = 0; i < numDices; i++) {
            if (i < 18) {
                dices.add(new Dice(Colour.ANSI_GREEN, 0));
            } else if (i < 36) {
                dices.add(new Dice(Colour.ANSI_BLUE, 0));
            } else if (i < 54) {
                dices.add(new Dice(Colour.ANSI_PURPLE, 0));
            } else if (i < 72) {
                dices.add(new Dice(Colour.ANSI_RED, 0));
            } else {
                dices.add(new Dice(Colour.ANSI_YELLOW, 0));
            }

        }


    }

    public List extract(int nPlayer) {
        // qua bisogna lanciare un'eccezione
        if (2 * nPlayer + 1 > dices.size())
            return null;

        List<Dice> extra = new ArrayList<Dice>();
        int nDice = 2 * nPlayer + 1;
        for (int i = 0; i < nDice; i++) {
            int casual = (int) (Math.random() * this.dices.size());
            dices.get(casual).rollDice();
            extra.add(dices.get(casual));
            dices.remove(casual);
        }
        System.out.println(extra);
        return extra;
    }

    public void insertDice(Dice dice) {
        this.dices.add(dice);
    }

    @Override
    public String toString() {
        System.out.println("dice in the bag:" + dices.size() + "\n");
        for (int i = 0; i < dices.size(); i++) {
            System.out.println(dices.get(i).toString());
        }

        return "";


    }





}
