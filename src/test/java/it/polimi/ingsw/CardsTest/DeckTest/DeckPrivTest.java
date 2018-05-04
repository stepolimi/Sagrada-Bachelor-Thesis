package it.polimi.ingsw.CardsTest.DeckTest;


import it.polimi.ingsw.Model.Cards.Decks.DeckPrivateObjective;
import it.polimi.ingsw.Model.Cards.PrivateObjective;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/*test made to :
------the remaining card in Deck has the correct number.
 */
public class DeckPrivTest {

    PrivateObjective card1 = new PrivateObjective("", "", null);
    PrivateObjective card2 = new PrivateObjective("", "", null);
    PrivateObjective card3 = new PrivateObjective("", "", null);
    PrivateObjective card4 = new PrivateObjective("", "", null);
    ArrayList<PrivateObjective> deckPriv = new ArrayList<PrivateObjective>();

    DeckPrivateObjective d = new DeckPrivateObjective(deckPriv);



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
