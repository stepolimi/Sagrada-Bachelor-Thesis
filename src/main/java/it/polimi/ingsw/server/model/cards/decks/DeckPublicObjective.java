package it.polimi.ingsw.server.model.cards.decks;

import it.polimi.ingsw.server.model.cards.objCards.*;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.serverCostants.Costants.DECK_PUBLIC_OBJECTIVES_SIZE;

public class DeckPublicObjective {
    private ArrayList<ObjectiveCard> deckPub;


    public DeckPublicObjective() {
        this.deckPub = new ArrayList<ObjectiveCard>();
        RowsObj card1 = new RowsObj("Colori diversi_riga", "Righe senza colori ripetuti", 6);
        RowsObj card2 = new RowsObj("Sfumature diverse_riga", "Righe senza sfumature ripetute", 5);
        ColumnsObj card3 = new ColumnsObj("Colori diversi_colonna", "Colonne senza colori ripetuti",5);
        ColumnsObj card4 = new ColumnsObj("Sfumature diverse_colonna", "Colonne senza sfumature ripetute",4);
        CoupleSetObj card5=new CoupleSetObj("Sfumature chiare", "Set di 1 & 2 ovunque", 1, 2 );
        CoupleSetObj card6=new CoupleSetObj("Sfumature medie", "Set di 3 & 4 ovunque", 3, 4);
        CoupleSetObj card7=new CoupleSetObj("Sfumature scure", "Set di 5 & 6 ovunque", 5, 6);
        SetObj card8= new SetObj("Sfumature diverse", "Set di dadi di ogni valore", 5);
        SetObj card9= new SetObj("Varietà di colore", "Set di dadi di ogni colore ovunque", 4);
        DiagonalObj card10= new DiagonalObj("Diagonali colorate", "Numero di dadi dello stesso colore diagonalmente adiacenti");

        this.deckPub.add(card1);
        this.deckPub.add(card2);
        this.deckPub.add(card3);
        this.deckPub.add(card4);
        this.deckPub.add(card5);
        this.deckPub.add(card6);
        this.deckPub.add(card7);
        this.deckPub.add(card8);
        this.deckPub.add(card9);
        this.deckPub.add(card10);



    }


    public List<ObjectiveCard> getDeckPub(){
        return this.deckPub;
    }

    public List<ObjectiveCard> extract()
    {
        ArrayList <ObjectiveCard> po = new ArrayList<ObjectiveCard>();
        int random;

        for(int i=0; i< DECK_PUBLIC_OBJECTIVES_SIZE; i++) {
            random = (int) (Math.random()*this.deckPub.size());
            po.add(this.deckPub.get(random));
            this.deckPub.remove(random);
        }
        return po;
    }
}
