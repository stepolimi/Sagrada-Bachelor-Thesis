package it.polimi.ingsw.Server.Model.game.states;

import it.polimi.ingsw.Server.Model.board.Board;
import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.DiceSpace;
import it.polimi.ingsw.Server.Model.board.Player;

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
    private DiceSpace dices;
    private Dice pendingDice;


    public Round(Player first, Board board){
        this.firstPlayer = first;
        this.board = board;
        this.currentRound = this;
    }

    public void roundInit(){
        playerIndex = board.getIndex(firstPlayer);
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

    public void startRound(){
        currentState.setActions(currentRound);
    }

    public void changeState(String action){
        currentState.execute(currentRound, action);
        currentState = states.get(currentState.nextState(currentRound,action));
        if(turnNumber < board.numPlayers() * 2) {
            currentState.setActions(currentRound);
        }
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer= player;
    }

    public void incrementTurnNumber(int i){
        this.turnNumber = this.turnNumber +i;
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

    public void setTurnNumber(int n){
        this.turnNumber= n;
    }

    public DiceSpace getDices() { return dices; }

    public void setDices(DiceSpace dices) { this.dices = dices; }

    public Dice getPendingDice() { return pendingDice; }

    public void setPendingDice(Dice dice){this.pendingDice = dice;}

    public String getCurrentState(){ return currentState.toString();}
}
