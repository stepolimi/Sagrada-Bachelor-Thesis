package it.polimi.ingsw.Server.Model.cards.ObjCards;
import it.polimi.ingsw.Server.Model.board.Schema;

public abstract class ObjectiveCard {

    private String name;
    private String description;

    public abstract int ScoreCard(Schema sch);
}
