package it.polimi.ingsw.serverTest.modelTest.cardsTest.deckTest;

import it.polimi.ingsw.server.model.cards.decks.DeckPublicObjective;
import it.polimi.ingsw.server.model.cards.objectiveCards.ObjectiveCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

class DeckObjTest {
    private List<ObjectiveCard> deckPub = new ArrayList<>();
    private DeckPublicObjective d = new DeckPublicObjective();

    @Test
    void correctExtract(){
        deckPub = d.extract();
        //Extracts the correct number of objectives
        assertEquals(3, deckPub.size());
        assertEquals(7, d.getDeckPub().size());

        //Every objective extracted is unique
        assertNotSame(deckPub.get(0),deckPub.get(1));
        assertNotSame(deckPub.get(1),deckPub.get(2));
        assertNotSame(deckPub.get(2),deckPub.get(0));
    }
}
