package it.polimi.ingsw.server.model.cards.decks;


import it.polimi.ingsw.server.model.cards.toolCards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.server.serverCostants.Costants.DECK_TOOL_CARDS_SIZE;
import static it.polimi.ingsw.server.serverCostants.Costants.NUM_TOOL_CARDS;

public class DeckToolsCard {
    private ToolCardBuilder toolCardBuilder;
    private List<ToolCard> toolCards;
    private List <Integer> toolCardAvabiles;

    public DeckToolsCard(){
        this.toolCards = new ArrayList<ToolCard>();
        this.toolCardAvabiles = new ArrayList<Integer>();
        this.toolCardBuilder = ToolCardBuilder.getToolCardBuilder();
        this.extract();
    }

    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    public void extract(){
        Random rand = new Random();

        for(int i=1;i<= NUM_TOOL_CARDS;i++)
            toolCardAvabiles.add(i);

        for (int i=0; i<DECK_TOOL_CARDS_SIZE; i++) {
            int index = rand.nextInt(toolCardAvabiles.size());
            toolCards.add(toolCardBuilder.buildToolCard(toolCardAvabiles.get(index)));
            toolCardAvabiles.remove(index);
        }
    }


}
