package it.polimi.ingsw.Cards.ObjCards;
import it.polimi.ingsw.Schema;

public abstract class ObjectiveCard {

    private String name;
    private String description;

    public abstract int ScoreCard(Schema sch);
}
