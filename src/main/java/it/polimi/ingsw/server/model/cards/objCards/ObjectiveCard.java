package it.polimi.ingsw.server.model.cards.objCards;
import it.polimi.ingsw.server.model.board.Schema;

public abstract class ObjectiveCard {

    private String name;
    private String description;

    public abstract int ScoreCard(Schema sch);
}
