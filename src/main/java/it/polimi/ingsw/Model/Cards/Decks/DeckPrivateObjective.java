package it.polimi.ingsw.Model.Cards.Decks;

import it.polimi.ingsw.Model.Cards.PrivateObjective;
import it.polimi.ingsw.Model.Colour;

import java.util.ArrayList;

public class DeckPrivateObjective {
    private ArrayList<PrivateObjective> deckPriv;


    public DeckPrivateObjective(ArrayList<PrivateObjective> deckPriv) {
        this.deckPriv = new ArrayList<PrivateObjective>();


        PrivateObjective card1 = new PrivateObjective("Sfumture Rosse - Private", "Somma dei valori su tutti i dadi rossi", Colour.ANSI_RED);
        PrivateObjective card2 = new PrivateObjective("Sfumture Gialle - Private", "Somma dei valori su tutti i dadi gialli", Colour.ANSI_YELLOW);
        PrivateObjective card3 = new PrivateObjective("Sfumture Verdi - Private", "Somma dei valori su tutti i dadi verdi", Colour.ANSI_GREEN);
        PrivateObjective card4 = new PrivateObjective("Sfumture Viola - Private", "Somma dei valori su tutti i dadi viola", Colour.ANSI_PURPLE);
        PrivateObjective card5 = new PrivateObjective("Sfumture Blu - Private", "Somma dei valori su tutti i dadi blu", Colour.ANSI_BLUE);

        this.deckPriv.add(card1);
        this.deckPriv.add(card2);
        this.deckPriv.add(card3);
        this.deckPriv.add(card4);
        this.deckPriv.add(card5);

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
