package it.polimi.ingsw.serverTest.modelTest.cardsTest.deckTest;

import it.polimi.ingsw.server.model.cards.decks.DeckToolsCard;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ToolsDeck {
    DeckToolsCard deck = new DeckToolsCard();

    @Test
    void correct_size(){
        assertEquals(12, deck.getToolCards().size());
    }
    @Test
    void correct_extract(){
        List<ToolCard> tools_choosen = deck.extract();
        assertEquals(9, deck.getToolCards().size());
        assertEquals(3, tools_choosen.size());

    }
}
