package it.polimi.ingsw.Model.CardsTest.DeckTest;

import it.polimi.ingsw.Model.Cards.Decks.DeckToolsCard;
import it.polimi.ingsw.Model.Cards.ToolCards.ToolCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class ToolsDeck {
    DeckToolsCard deck = new DeckToolsCard();

    @Test
    void correct_size(){
        assertEquals(12, deck.getToolCards().size());
    }
    @Test
    void correct_extract(){
        ArrayList<ToolCard> tools_choosen = deck.extract();
        assertEquals(9, deck.getToolCards().size());
        assertEquals(3, tools_choosen.size());

    }
}
