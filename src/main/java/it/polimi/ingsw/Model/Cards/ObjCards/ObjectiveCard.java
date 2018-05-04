package it.polimi.ingsw.Model.Cards.ObjCards;
import it.polimi.ingsw.Model.Schema;

public abstract class ObjectiveCard {

    private String name;
    private String description;

    public abstract int ScoreCard(Schema sch);
}
