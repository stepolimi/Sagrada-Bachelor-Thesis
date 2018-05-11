package it.polimi.ingsw.Model.CardsTest.DeckTest;


import it.polimi.ingsw.Server.Model.cards.Decks.DeckPrivateObjective;
import it.polimi.ingsw.Server.Model.cards.PrivateObjective;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

/*test made to :
------the remaining card in Deck has the correct number.
 */
public class DeckPrivTest {

    PrivateObjective card1 = new PrivateObjective();
    PrivateObjective card2 = new PrivateObjective();

    DeckPrivateObjective d = new DeckPrivateObjective();



    @Test
    public void correctExtract(){
        card1=d.extract();
        assertEquals(4, d.getDeckPriv().size());

    }

    @Test
    public void correct_extract(){
        card1=d.extract();
        card2=d.extract();
        assertEquals(3, d.getDeckPriv().size());
    }

}
