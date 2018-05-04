package it.polimi.ingsw.CardsTest.DeckTest;

import it.polimi.ingsw.Model.Cards.Decks.DeckPublicObjective;
import it.polimi.ingsw.Model.Cards.ObjCards.ObjectiveCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/*test made to :
------verify that extract function returns the right number of card.
------the remaining card in Deck has the correct number
 */

public class DeckObjTest {

    ArrayList<ObjectiveCard> deckPub = new ArrayList<ObjectiveCard>();
    DeckPublicObjective d = new DeckPublicObjective(deckPub);


    @Test
    public void correctExtract(){
        deckPub= d.extract(3);
        assertEquals(3, deckPub.size());
        assertEquals(7, d.getDeckPub().size());

    }

    @Test
    public void correctExtract2(){
        deckPub = d.extract(4);
        assertEquals(3, deckPub.size());
        assertEquals(7, d.getDeckPub().size());


    }
    @Test
    public void correctExtract3(){
        deckPub = d.extract(4);
        assertEquals(3, deckPub.size());
        assertEquals(7, d.getDeckPub().size());


    }

    @Test
    public void correctExtract4(){
        deckPub = d.extract(1);
        assertEquals(2, deckPub.size());
        assertEquals(7, d.getDeckPub().size());


    }




}
