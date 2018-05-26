package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.timer.TurnTimer;

import java.util.*;

import static it.polimi.ingsw.costants.GameCreationMessages.startTurn;
import static it.polimi.ingsw.costants.LoginMessages.timerPing;
import static it.polimi.ingsw.costants.TimerCostants.TurnTimerValue;

public class Round extends Observable{
    private Player firstPlayer;
    private Board board;
    private Player currentPlayer;
    private int playerIndex ;
    private int turnNumber = 0;
    private HashMap<String,State> states;
    private State currentState ;
    private Dice pendingDice;
    private int usingTool = 0;
    private List<String> legalActions;

    private TurnTimer turnTimer;
    private Timer timer;
    private Long startingTime = 0L;


    public Round(Player first, Board board){
        states = new HashMap<String, State>();
        legalActions = new ArrayList<String>();
        firstPlayer = first;
        this.board = board;
    }

    public void roundInit(){
        playerIndex = board.getIndex(firstPlayer);
        currentPlayer = firstPlayer;
        states.put("StartRoundState",new StartRoundState());
        currentState = states.get("StartRoundState");
        states.put("ExtractDiceState",new ExtractDiceState());
        states.put("UseCardState",new UseCardState());
        states.put("RollDiceState",new RollDiceState());
        states.put("ChangeValueState",new ChangeValueState());
        states.put("PickDiceState",new PickDiceState());
        states.put("PlaceDiceState",new PlaceDiceState());
        states.put("EndTurnState",new EndTurnState());

        currentState.execute(this,null);
        notifyChanges("state");

        startingTime = System.currentTimeMillis();
        turnTimer = new TurnTimer(TurnTimerValue,this);
        timer = new Timer();
        timer.schedule(turnTimer,0L,5000L);
    }

    public void execute(List action){
        timer.cancel();
        if (usingTool == 0) {
            currentState = states.get(currentState.nextState(this, action));
            currentState.execute(this, action);
        }else{
            currentState.execute(this, action);
            currentState = states.get(currentState.nextState(this, action));
        }
        notifyChanges("state");
        startingTime = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(turnTimer,0L,5000L);
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

    public void setLegalActions(List<String> legalActions){ this.legalActions = legalActions; }

    public void notifyChanges(String string){
        List action = new ArrayList();
        if(string.equals("state")) {
            if (currentState.toString().equals("StartRoundState")) {
                action.add(startTurn);
                action.add(currentPlayer.getNickname());
                action.add("ExtractDice");
            } else if (currentState.toString().equals("EndTurnState")) {
                action.add(startTurn);
                action.add("PickDice");
                action.add("UseToolCard");
                action.add("EndTurn");
            }
            setChanged();
            notifyObservers(action);
        }else if(string.equals(timerPing)){
            action.add(timerPing + "Turn");
            action.add(currentPlayer.getNickname());
            action.add(((Long)(TurnTimerValue - (System.currentTimeMillis() - startingTime)/1000)).toString());
        }else{
            action.add("TimerScaduto");                     //provvisorio
            action.add(currentPlayer.getNickname());
            setChanged();
            notifyObservers(action);
            currentState = states.get("EndTurnState");
            currentState.execute(this, new ArrayList());
        }
    }
}
