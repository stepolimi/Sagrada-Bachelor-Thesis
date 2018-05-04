package it.polimi.ingsw.Model.game.states;

public class EndTurnState extends State {
    private String state = "EndTurnState";
    private static String next ;

    @Override
    public void setActions(Round round){
        //confirm end turn
    }

    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){
        round.incrementTurnNumber(1);
        if(round.getTurnNumber() < round.getBoard().numPlayers())
            round.setPlayerIndex((round.getPlayerIndex() + 1) % (round.getBoard().numPlayers() )) ;
        else if (round.getTurnNumber() > round.getBoard().numPlayers()){
            if (round.getPlayerIndex() == 0)
                round.setPlayerIndex(round.getBoard().numPlayers() - 1);
            else
                round.setPlayerIndex (round.getPlayerIndex() - 1);
        }
        round.setCurrentPlayer (round.getBoard().getPlayer(round.getPlayerIndex()));
        return action + "State";
    }

    @Override

    public String toString (){return state; }
}
