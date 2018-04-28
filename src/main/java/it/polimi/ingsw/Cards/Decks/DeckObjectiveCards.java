package it.polimi.ingsw.Cards.Decks;

import it.polimi.ingsw.Cards.ObjCards.*;

import java.util.ArrayList;

public class DeckObjectiveCards {
    private ArrayList<ObjectiveCard> deckPub;


    public DeckObjectiveCards(ArrayList<ObjectiveCard> deckPub) {
        this.deckPub = new ArrayList<ObjectiveCard>();
        RowsObj card1 = new RowsObj("Colori diversi Riga", "Righe senza colori ripetuti", 6);
        RowsObj card2 = new RowsObj("Sfumature diverse- Riga", "Righe senza sfumature ripetute", 5);
        ColumnsObj card3 = new ColumnsObj("Colori diversi - Colonna", "Colonne senza colori ripetuti",5);
        ColumnsObj card4 = new ColumnsObj("Sfumature diverse - Colonna", "Colonne senza sfumature ripetute",4);
        CoupleSetObj card5=new CoupleSetObj("Sfumature Chiare", "Set di 1 & 2 ovunque", 1, 2 );
        CoupleSetObj card6=new CoupleSetObj("Sfumature Medie", "Set di 3 & 4 ovunque", 3, 4);
        CoupleSetObj card7=new CoupleSetObj("Sfumature Scure", "Set di 5 & 6 ovunque", 5, 6);
        SetObj card8= new SetObj("Sfumture Diverse", "Set di dadi di ogni valore", 5);
        SetObj card9= new SetObj("Varietà di Colore", "Set di dadi di ogni colore ovunque", 4);
        DiagonalObj card10= new DiagonalObj("Diagonali Colorate", "Numero di dadi dello stesso colore diagonalmente adiacenti");

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
}