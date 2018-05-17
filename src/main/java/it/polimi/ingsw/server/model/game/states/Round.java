package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

public class Round extends Observable{
    private Player firstPlayer;
    private Board board;
    private Player currentPlayer;
    private int playerIndex ;
    private int turnNumber = 0;
    private HashMap<String,State> states= new HashMap<String, State>();
    private State currentState ;
    private Dice pendingDice;
    private boolean first = true;
    private int usingTool = 0;


    public Round(Player first, Board board){
        this.firstPlayer = first;
        this.board = board;
    }

    public void roundInit(){
        playerIndex = board.getIndex(firstPlayer);
        currentPlayer = firstPlayer;
        states.put("ExtractDiceState",new ExtractDiceState());
        currentState = states.get("ExtractDiceState");
        states.put("UseCardState",new UseCardState());
        states.put("RollDiceState",new RollDiceState());
        states.put("ChangeValueState",new ChangeValueState());
        states.put("PickDiceState",new PickDiceState());
        states.put("PlaceDiceState",new PlaceDiceState());
        states.put("EndTurnState",new EndTurnState());

        setChanged();
        notifyObservers("primo stato (ExtractDice)");       //immagino che passerà currentState
    }

    public void execute(List action){
        if (usingTool == 0) {
            if (first)
                first = false;
            else
                currentState = states.get(currentState.nextState(this, action));
            currentState.execute(this, action);
        }else{
            currentState.execute(this, action);
            currentState = states.get(currentState.nextState(this, action));
        }

        setChanged();
        notifyObservers("stato attuale");       //immagino che passerà currentState
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

    public Dice getPendingDice() { return pendingDice; }

    public void setPendingDice(Dice dice){this.pendingDice = dice;}

    public String getCurrentState(){ return currentState.toString();}

    public void setUsingTool(int using){ this.usingTool = using;}

    public int getUsingTool(){return usingTool;}
}
