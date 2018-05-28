package it.polimi.ingsw.server.model.game.states;

import java.util.ArrayList;
import java.util.List;

public class EndTurnState implements State {
    private static String state = "EndTurnState";

    public void execute(Round round, List action){
        round.setInsertedDice(false);
        round.setUsedCard(false);
        do {
            round.incrementTurnNumber(1);
            if (round.getTurnNumber() < round.getBoard().numPlayers())
                if (round.getPlayerIndex() == (round.getBoard().numPlayers() - 1))
                    round.setPlayerIndex(0);
                else
                    round.setPlayerIndex(round.getPlayerIndex() + 1);
            else if (round.getTurnNumber() > round.getBoard().numPlayers()) {
                if (round.getPlayerIndex() == 0)
                    round.setPlayerIndex(round.getBoard().numPlayers() - 1);
                else
                    round.setPlayerIndex(round.getPlayerIndex() - 1);
            }
            round.setCurrentPlayer(round.getBoard().getPlayer(round.getPlayerIndex()));
        }while (! round.getCurrentPlayer().isConnected());
        System.out.println("turn ended\n" + " ---");
        giveLegalActions(round);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        legalActions.add("InsertDice");
        legalActions.add("UseCard");
        legalActions.add("EndTurn");
        round.setLegalActions(legalActions);
    }

    @Override
    public String toString (){return state; }
}
