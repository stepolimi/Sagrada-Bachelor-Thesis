package it.polimi.ingsw.states;

public class PlaceDiceState extends State{
    private String next;

    @Override
    public void execute(Round round){
        System.out.println(round.getCurrentPlayer().getNickname() + "Place");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        next = "EndTurnState";
        return next;
    }
}
