package it.polimi.ingsw.server.model.cards.objCards;
import it.polimi.ingsw.server.model.board.Schema;

public abstract class ObjectiveCard {
    public abstract int scoreCard(Schema sch);

    public abstract String getName();
}
