package it.polimi.ingsw.server.model.cards.decks;

import it.polimi.ingsw.server.model.cards.PrivateObjective;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.server.model.builders.PrivateObjectiveBuilder.buildPrivateObjective;
import static it.polimi.ingsw.server.costants.Constants.NUM_PRIVATE_OBJECTIVES;

public class DeckPrivateObjective {
    private ArrayList<PrivateObjective> privateObjectives;
    private List<Integer> privateObjectivesAvailable;


    public DeckPrivateObjective(int nPlayers) {
        privateObjectives = new ArrayList<>();
        privateObjectivesAvailable = new ArrayList<>();
        createPrivateObjectives(nPlayers);
    }

    private void createPrivateObjectives(int nPlayers){
        Random rand = new Random();

        for (int i = 1; i <= NUM_PRIVATE_OBJECTIVES; i++)
            privateObjectivesAvailable.add(i);
        for (int i = 0; i < nPlayers; i++) {
            int index = rand.nextInt(privateObjectivesAvailable.size());
            privateObjectives.add(buildPrivateObjective(privateObjectivesAvailable.get(index)));
            privateObjectivesAvailable.remove(index);
        }
    }

    public PrivateObjective extract(int playerIndex) {
        return privateObjectives.get(playerIndex);
    }

    public List<PrivateObjective> getDeckPrivate() {
        return privateObjectives;
    }

}
