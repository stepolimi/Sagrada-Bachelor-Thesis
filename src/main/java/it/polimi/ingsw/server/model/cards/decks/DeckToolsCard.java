package it.polimi.ingsw.server.model.cards.decks;


import it.polimi.ingsw.server.model.cards.toolCards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.server.model.builders.ToolCardBuilder.buildToolCard;
import static it.polimi.ingsw.server.costants.Constants.DECK_TOOL_CARDS_SIZE;
import static it.polimi.ingsw.server.costants.Constants.NUM_TOOL_CARDS;

public class DeckToolsCard {
    private List<ToolCard> toolCards;
    private List<Integer> toolCardsAvailable;

    public DeckToolsCard() {
        this.toolCards = new ArrayList<>();
        this.toolCardsAvailable = new ArrayList<>();
        this.extract();
    }

    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    public void extract() {
        Random rand = new Random();

        for (int i = 1; i <= NUM_TOOL_CARDS; i++)
            toolCardsAvailable.add(i);

        for (int i = 0; i < DECK_TOOL_CARDS_SIZE; i++) {
            int index = rand.nextInt(toolCardsAvailable.size());
            toolCards.add(buildToolCard(toolCardsAvailable.get(index)));
            toolCardsAvailable.remove(index);
        }
    }


}
