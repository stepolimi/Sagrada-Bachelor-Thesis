package it.polimi.ingsw.serverTest.modelTest.cardsTest.deckTest;


import it.polimi.ingsw.server.model.cards.decks.DeckPrivateObjective;
import it.polimi.ingsw.server.model.cards.PrivateObjective;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

class DeckPrivTest {
    private PrivateObjective card1;
    private DeckPrivateObjective d = new DeckPrivateObjective(4);

    @Test
    void singleCorrectExtract(){
        assertEquals(4,d.getDeckPrivate().size());
        card1=d.extract(0);
    }

    @Test
    void CorrectExtracts(){
        assertEquals(4, d.getDeckPrivate().size());
        card1=d.extract(0);
        PrivateObjective card2 = d.extract(1);
        PrivateObjective card3 = d.extract(2);
        PrivateObjective card4 = d.extract(3);

        assertNotSame(card1.getColour(),card2.getColour());
        assertNotSame(card1.getColour(),card3.getColour());
        assertNotSame(card1.getColour(),card4.getColour());
        assertNotSame(card2.getColour(),card3.getColour());
        assertNotSame(card2.getColour(),card4.getColour());
        assertNotSame(card3.getColour(),card4.getColour());
    }
}
