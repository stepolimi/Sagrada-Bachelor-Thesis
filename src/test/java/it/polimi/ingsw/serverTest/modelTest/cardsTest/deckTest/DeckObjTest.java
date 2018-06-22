package it.polimi.ingsw.serverTest.modelTest.cardsTest.deckTest;

import it.polimi.ingsw.server.model.cards.decks.DeckPublicObjective;
import it.polimi.ingsw.server.model.cards.objectiveCards.ObjectiveCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/*test made to :
------verify that extract function returns the right number of card.
------the remaining card in Deck has the correct number
 */

public class DeckObjTest {

    List<ObjectiveCard> deckPub = new ArrayList<ObjectiveCard>();
    DeckPublicObjective d = new DeckPublicObjective();


    @Test
    public void correctExtract(){
        deckPub= d.extract();
        assertEquals(3, deckPub.size());
        assertEquals(7, d.getDeckPub().size());

    }

    @Test
    public void correctExtract2(){
        deckPub = d.extract();
        assertEquals(3, deckPub.size());
        assertEquals(7, d.getDeckPub().size());


    }
    @Test
    public void correctExtract3(){
        deckPub = d.extract();
        assertEquals(3, deckPub.size());
        assertEquals(7, d.getDeckPub().size());


    }



}
