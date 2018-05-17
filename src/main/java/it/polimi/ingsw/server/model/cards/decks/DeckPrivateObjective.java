package it.polimi.ingsw.server.model.cards.decks;

import it.polimi.ingsw.server.model.cards.PrivateObjective;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeckPrivateObjective {
    private ArrayList<PrivateObjective> deckPriv;


    public DeckPrivateObjective() {
        this.deckPriv = new ArrayList<PrivateObjective>();

        for(int i = 1; i < 6; i++){
            PrivateObjective p = new PrivateObjective();
            try {
               this.deckPriv.add(new PrivateObjective().PrivateInit(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public List<PrivateObjective> getDeckPriv(){
        return this.deckPriv;
    }

    public PrivateObjective extract()
    {
        PrivateObjective po;
        int random;
        random = (int) ( Math.random() * deckPriv.size())  ;
        po = this.deckPriv.get(random);
        deckPriv.remove(random);
        return po;
    }
}
