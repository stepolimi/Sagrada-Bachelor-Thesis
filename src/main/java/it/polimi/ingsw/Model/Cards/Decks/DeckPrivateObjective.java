package it.polimi.ingsw.Model.Cards.Decks;

import it.polimi.ingsw.Model.Cards.PrivateObjective;

import java.io.IOException;
import java.util.ArrayList;

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

    public ArrayList<PrivateObjective> getDeckPriv(){
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
