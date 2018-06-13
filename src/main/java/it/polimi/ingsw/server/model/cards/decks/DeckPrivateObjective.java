package it.polimi.ingsw.server.model.cards.decks;

import it.polimi.ingsw.server.model.cards.PrivateObjective;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeckPrivateObjective {
    private ArrayList<PrivateObjective> privateObjectives;


    public DeckPrivateObjective() {
        privateObjectives = new ArrayList<PrivateObjective>();

        for (int i = 1; i < 6; i++) {
            try {
                privateObjectives.add(new PrivateObjective().privateInit(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PrivateObjective> getDeckPrivate() {
        return privateObjectives;
    }

    public PrivateObjective extract() {
        PrivateObjective po;
        int random;
        random = (int) (Math.random() * privateObjectives.size());
        po = this.privateObjectives.get(random);
        privateObjectives.remove(random);
        return po;
    }
}
