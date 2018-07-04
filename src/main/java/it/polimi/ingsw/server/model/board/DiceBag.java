
package it.polimi.ingsw.server.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.server.costants.Constants.TOT_DICES;

public class DiceBag {
    private final List<Dice> dices;

    /**
     * Creates 90 dices, 18 for each colour.
     */
    public DiceBag() {
        dices = new ArrayList<>();

        for (int i = 0; i < TOT_DICES; i++) {
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

    /**
     * Extracts randomly 2* number of players in the game + 1 dices and sets randomly their value.
     * @param nPlayer is the number of players in the game.
     * @return a list with the extracted dices.
     */
    public List<Dice> extract(int nPlayer) {
        List<Dice> extra = new ArrayList<>();
        int nDice = 2 * nPlayer + 1;

        if (2 * nPlayer + 1 > dices.size())
            return extra;

        for (int i = 0; i < nDice; i++) {
            Random random = new Random();
            int casual = random.nextInt(this.dices.size());
            dices.get(casual).rollDice();
            extra.add(dices.get(casual));
            dices.remove(casual);
        }
        return extra;
    }

    /**
     * Resets the value of the dice to 0 and adds it to the list of dice.
     * @param dice is a dice that will be added to the lst of dice.
     */
    public void insertDice(Dice dice) {
        dice.resetValue();
        this.dices.add(dice);
    }

    public List<Dice> getDices() {
        return this.dices;
    }

    /**
     * Selects randomly a dice from the list and sets randomly it's value
     * @return a dice from the list of dice.
     */
    public Dice takeDice() {
        Dice d;
        Random rand = new Random();
        int random = rand.nextInt(this.dices.size());
        d = dices.get(random);
        dices.remove(random);
        d.rollDice();
        return d;
    }

}
