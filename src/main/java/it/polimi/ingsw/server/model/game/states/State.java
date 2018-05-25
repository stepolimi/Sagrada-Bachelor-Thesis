package it.polimi.ingsw.server.model.game.states;

import java.util.List;

public interface State {
    void execute(Round round, List action);
    String nextState(Round round, List action);

}
