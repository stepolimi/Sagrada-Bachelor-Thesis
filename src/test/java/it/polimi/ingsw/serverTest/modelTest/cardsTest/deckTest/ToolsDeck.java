package it.polimi.ingsw.serverTest.modelTest.cardsTest.deckTest;

import it.polimi.ingsw.server.model.cards.decks.DeckToolsCard;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToolsDeck {
    DeckToolsCard deck = new DeckToolsCard();

    @Test
    void correct_size(){
        assertEquals(3, deck.getToolCards().size());
    }

    @Test
    void correct_extract(){
        List<ToolCard> toolCards = deck.getToolCards();
        assertEquals(3, toolCards.size());
        assertTrue(toolCards.get(0)!=toolCards.get(1));
        assertTrue(toolCards.get(1)!=toolCards.get(2));
        assertTrue(toolCards.get(2)!=toolCards.get(0));
    }
}
