package it.polimi.ingsw.server.model.cards.objective.cards;
import it.polimi.ingsw.server.model.board.Schema;

public interface ObjectiveCard {
    int scoreCard(Schema sch);

    String getName();
}
