package it.polimi.ingsw.serverTest.modelTest.cardsTest.deckTest;


import it.polimi.ingsw.server.model.cards.decks.DeckPrivateObjective;
import it.polimi.ingsw.server.model.cards.PrivateObjective;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/*test made to :
------the remaining card in Deck has the correct number.
 */
class DeckPrivTest {
    private PrivateObjective card1;
    private PrivateObjective card2;
    private PrivateObjective card3;
    private PrivateObjective card4;
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
        card2=d.extract(1);
        card3=d.extract(2);
        card4=d.extract(3);

        assertNotSame(card1.getColour(),card2.getColour());
        assertNotSame(card1.getColour(),card3.getColour());
        assertNotSame(card1.getColour(),card4.getColour());
        assertNotSame(card2.getColour(),card3.getColour());
        assertNotSame(card2.getColour(),card4.getColour());
        assertNotSame(card3.getColour(),card4.getColour());
    }
}
