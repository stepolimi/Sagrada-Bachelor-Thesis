package it.polimi.ingsw.server.model.cards.decks;

import it.polimi.ingsw.server.model.cards.objective.cards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.server.costants.Constants.DECK_PUBLIC_OBJECTIVES_SIZE;

public class DeckPublicObjective {
    private final ArrayList<ObjectiveCard> deckPub;

    /**
     * Creates the public objectives.
     */
    public DeckPublicObjective() {
        this.deckPub = new ArrayList<>();
        RowsObj card1 = new RowsObj("Colori diversi_riga", 6);
        RowsObj card2 = new RowsObj("Sfumature diverse_riga", 5);
        ColumnsObj card3 = new ColumnsObj("Colori diversi_colonna", 5);
        ColumnsObj card4 = new ColumnsObj("Sfumature diverse_colonna", 4);
        CoupleSetObj card5 = new CoupleSetObj("Sfumature chiare", 1, 2);
        CoupleSetObj card6 = new CoupleSetObj("Sfumature medie", 3, 4);
        CoupleSetObj card7 = new CoupleSetObj("Sfumature scure", 5, 6);
        SetObj card8 = new SetObj("Sfumature diverse", 5);
        SetObj card9 = new SetObj("Varietà di colore", 4);
        DiagonalObj card10 = new DiagonalObj("Diagonali colorate");

        this.deckPub.add(card1);
        this.deckPub.add(card2);
        this.deckPub.add(card3);
        this.deckPub.add(card4);
        this.deckPub.add(card5);
        this.deckPub.add(card6);
        this.deckPub.add(card7);
        this.deckPub.add(card8);
        this.deckPub.add(card9);
        this.deckPub.add(card10);

    }

    public List<ObjectiveCard> getDeckPub() {
        return this.deckPub;
    }

    /**
     * Selects 3 random public objectives from those available.
     * @return a list with the 3 public objectives selected
     */
    public List<ObjectiveCard> extract() {
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
        Random random = new Random();
        int index;

        for (int i = 0; i < DECK_PUBLIC_OBJECTIVES_SIZE; i++) {
            index = random.nextInt(DECK_PUBLIC_OBJECTIVES_SIZE);
            objectiveCards.add(this.deckPub.get(index));
            this.deckPub.remove(index);
        }
        return objectiveCards;
    }
}
