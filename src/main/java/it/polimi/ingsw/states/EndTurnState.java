package it.polimi.ingsw.states;

public class EndTurnState extends State {
    private static String next = "InitialState";

    @Override
    public void execute(Round round){
        System.out.println(round.getCurrentPlayer().getNickname() + "End");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        round.setTurnNumber(1);
        if(round.getTurnNumber() < round.getBoard().numPlayers())
            round.setPlayerIndex((round.getPlayerIndex() + 1) % (round.getBoard().numPlayers())) ;
        else if (round.getTurnNumber() > round.getBoard().numPlayers()){
            if (round.getPlayerIndex() == 0)
                round.setPlayerIndex(round.getBoard().numPlayers() - 1);
            else
                round.setPlayerIndex (round.getPlayerIndex() - 1);
        }
        round.setCurrentPlayer (round.getBoard().getPlayer(round.getPlayerIndex()));
        return next;
    }
}
