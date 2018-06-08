package it.polimi.ingsw.server.model.game.states;

import java.util.ArrayList;
import java.util.List;

public class EndTurnState implements State {
    private static String state = "EndTurnState";

    public void execute(Round round, List action){
        round.setInsertedDice(false);
        round.setUsedCard(false);
        round.setBonusInsertDice(false);
        round.setMovedDiceColour(null);
        do {
            if(round.getTurnNumber() == round.getBoard().getPlayerList().size()*2 -1){
                round.getBoard().getRoundTrack().insertDices(round.getBoard().getDiceSpace().getListDice(),round.getRoundManager().getRoundNumber() - 1);
                if(round.getRoundManager().getRoundNumber() <=10) {
                    round.getRoundManager().startNewRound();
                }
                return;
            }
            round.getPlayersOrder().remove(0);
            round.setCurrentPlayer(round.getPlayersOrder().get(0));
            round.incrementTurnNumber();
        }while (! round.getCurrentPlayer().isConnected());
        System.out.println("turn ended\n" + " ---");
        giveLegalActions(round);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        legalActions.add("InsertDice");
        legalActions.add("UseToolCard");
        legalActions.add("EndTurn");
        round.setLegalActions(legalActions);
    }

    @Override
    public String toString (){return state; }
}
