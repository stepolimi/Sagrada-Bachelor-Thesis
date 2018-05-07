package it.polimi.ingsw.Model.Cards.Decks;

import it.polimi.ingsw.Model.Cards.ToolCards.*;

import java.util.ArrayList;

public class DeckToolsCard {
    private ArrayList<ToolCard> toolCards;

    public DeckToolsCard(){
        this.toolCards = new ArrayList<ToolCard>();

        Pinza card1= new Pinza();
        PennelloEglomise card2 = new PennelloEglomise();
        Alesatore card3= new Alesatore();
        Lathekin card4= new Lathekin();
        TaglierinaCircolare card5= new TaglierinaCircolare();
        PennelloPastaSalda card6= new PennelloPastaSalda();
        Martelletto card7= new Martelletto();
        Tenaglia card8= new Tenaglia();
        Riga card9= new Riga();
        Tampone card10= new Tampone();
        Diluente card11=new Diluente();
        TaglierinaManuale card12= new TaglierinaManuale();

        toolCards.add(card1);
        toolCards.add(card2);
        toolCards.add(card3);
        toolCards.add(card4);
        toolCards.add(card5);
        toolCards.add(card6);
        toolCards.add(card7);
        toolCards.add(card8);
        toolCards.add(card9);
        toolCards.add(card10);
        toolCards.add(card11);
        toolCards.add(card12);

    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    public ArrayList extract(){
        ArrayList <ToolCard> tools = new ArrayList<ToolCard>();
        int random;

        for (int i=0; i<3; i++) {
            random = (int) (Math.random() * this.toolCards.size());
            tools.add(this.toolCards.get(random));
            this.toolCards.remove(random);

        }

        return tools;
    }


}
