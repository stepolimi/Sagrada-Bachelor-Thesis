package it.polimi.ingsw.states;

import it.polimi.ingsw.Board;
import it.polimi.ingsw.Player;

import java.util.HashMap;

public class Round {
    private Player firstPlayer;
    private Board board;
    private Player currentPlayer;
    private int playerIndex ;
    private int turnNumber = 0;
    private HashMap<String,State> states= new HashMap<String, State>();
    private State currentState ;
    private Round currentRound;

    public Round(Player first, Board board){
        this.firstPlayer = first;
        this.board= board;
        this.playerIndex = board.getIndex(firstPlayer);
    }

    public void roundInit(){
        currentPlayer = firstPlayer;
        states.put("ExtractDiceState",new ExtractDiceState());
        currentState = states.get("ExtractDiceState");
        states.put("InitialState",new InitialState());
        states.put("UseCardState",new UseCardState());
        states.put("RollDiceState",new RollDiceState());
        states.put("ChangeValueState",new ChangeValueState());
        states.put("PickDiceState",new PickDiceState());
        states.put("PlaceDiceState",new PlaceDiceState());
        states.put("EndTurnState",new EndTurnState());
    }

    public void setReference(Round r){
        this.currentRound= r;
    }
    public void executeRound(){
        currentState.execute(currentRound);
    }

    public void changeState(){
        currentState = states.get(currentState.nextState(currentRound));
        if(turnNumber < board.numPlayers() * 2) {
            currentState.execute(currentRound);
        }
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer= player;
    }

    public void setTurnNumber(int i){
        turnNumber = turnNumber +i;
    }
    public int getTurnNumber(){
        return turnNumber;
    }

    public int getPlayerIndex(){
        return playerIndex;
    }

    public void setPlayerIndex(int index){
        this.playerIndex = index;
    }

    public Board getBoard(){
        return board;
    }

}
