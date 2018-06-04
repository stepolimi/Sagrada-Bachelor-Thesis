package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.timer.GameTimer;
import it.polimi.ingsw.server.timer.TimedComponent;

import java.util.*;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameCreationMessages.START_TURN;
import static it.polimi.ingsw.costants.LoginMessages.TIMER_ELAPSED;
import static it.polimi.ingsw.costants.LoginMessages.TIMER_PING;
import static it.polimi.ingsw.costants.TimerCostants.TURN_TIMER_VALUE;
import static it.polimi.ingsw.costants.TimerCostants.TURN_TIMER_PING;

public class Round extends Observable implements TimedComponent {
    private Player firstPlayer;
    private Board board;
    private Player currentPlayer;
    private int playerIndex ;
    private int turnNumber = 0;
    private HashMap<String,State> states;
    private State currentState ;
    private Dice pendingDice;
    private int usingTool = 0;
    private List<List<String>> nextActions;
    private List<String> legalActions;
    private RoundManager roundManager;

    private boolean usedCard;
    private boolean insertedDice;

    private GameTimer turnTimer;
    private Timer timer;
    private Long startingTime = 0L;


    public Round(Player first, Board board,RoundManager roundManager){
        this.roundManager = roundManager;
        states = new HashMap<String, State>();
        legalActions = new ArrayList<String>();
        firstPlayer = first;
        this.board = board;
        usedCard = false;
        insertedDice = false;
        nextActions = new ArrayList<List<String>>();
    }

    public void roundInit(){
        playerIndex = board.getIndex(firstPlayer);
        currentPlayer = firstPlayer;
        states.put("ExtractDiceState",new ExtractDiceState());
        currentState = states.get("ExtractDiceState");
        states.put("InsertDiceState",new InsertDiceState());
        states.put("UseToolCardState",new UseToolCardState());
        states.put("MoveDiceState",new MoveDiceState());
        states.put("RollDiceState",new RollDiceState());
        states.put("ChangeValueState",new ChangeValueState());
        states.put("DraftDiceState",new DraftDiceState());
        states.put("PlaceDiceState",new PlaceDiceState());
        states.put("SwapDiceState",new PlaceDiceState());
        states.put("EndTurnState",new EndTurnState());

        System.out.println("new round started\n" + " ---");
        currentState.execute(this,null);
        notifyChanges("newState");

        startingTime = System.currentTimeMillis();
        turnTimer = new GameTimer(TURN_TIMER_VALUE,this);
        timer = new Timer();
        timer.schedule(turnTimer,0L,5000L);
    }

    public void execute(List action){
        if(legalActions.contains(action.get(0))) {
            timer.cancel();
            currentState = states.get(currentState.nextState(this, action));
            currentState.execute(this, action);
            notifyChanges("newState");
            startingTime = System.currentTimeMillis();
            turnTimer = new GameTimer(TURN_TIMER_VALUE,this);
            timer = new Timer();
            timer.schedule(turnTimer, 0L, 5000L);
        }else{
            System.out.println("can't perform: " + action.get(0) + " now\n" + " ---");
            notifyChanges(ILLEGAL_ACTION);
        }
    }

    public Player getCurrentPlayer(){ return currentPlayer; }

    public void setCurrentPlayer(Player player){ this.currentPlayer= player; }

    public void incrementTurnNumber(int i){ this.turnNumber = this.turnNumber +i; }

    public int getTurnNumber(){ return turnNumber; }

    public int getPlayerIndex(){ return playerIndex; }

    public void setPlayerIndex(int index){ this.playerIndex = index; }

    public Board getBoard(){ return board; }

    public void setTurnNumber(int n){ this.turnNumber= n; }

    public Dice getPendingDice() { return pendingDice; }

    public void setPendingDice(Dice dice){this.pendingDice = dice;}

    public String getCurrentState(){ return currentState.toString();}

    public void setUsingTool(int using){ this.usingTool = using;}

    public int getUsingTool(){return usingTool;}

    public void setLegalActions(List<String> legalActions){ this.legalActions = legalActions; }

    public boolean isUsedCard() { return usedCard; }

    public void setUsedCard(boolean usedCard) { this.usedCard = usedCard; }

    public boolean isInsertedDice() { return insertedDice; }

    public void setInsertedDice(boolean insertedDice) { this.insertedDice = insertedDice; }

    public void setNextActions(List<List<String>> nextActions) { this.nextActions = nextActions; }

    public List<List<String>> getNextActions() { return nextActions; }

    public void notifyChanges(String string){
        List<String> action = new ArrayList<String>();
        if(string.equals("newState")) {
            if (currentState.toString().equals("ExtractDiceState")) {
                action.add(START_ROUND);
                setChanged();
                notifyObservers(action);
                action.clear();

                action.add(START_TURN);
                action.add(currentPlayer.getNickname());
                setChanged();
                notifyObservers(action);
            } else if (currentState.toString().equals("EndTurnState")) {
                action.add(START_TURN);
                action.add(currentPlayer.getNickname());
                setChanged();
                notifyObservers(action);
            }
            action.clear();
            action.add(SET_ACTIONS);
            action.add(currentPlayer.getNickname());
            action.addAll(legalActions);
        } else if(string.equals(ILLEGAL_ACTION)){
            action.add(SET_ACTIONS);
            action.add(currentPlayer.getNickname());
            action.addAll(legalActions);
        } else if(string.equals(TIMER_PING)){
            action.add(TURN_TIMER_PING);
            action.add(currentPlayer.getNickname());
            action.add(((Long)(TURN_TIMER_VALUE - (System.currentTimeMillis() - startingTime)/1000)).toString());
        } else if(string.equals(TIMER_ELAPSED)){
            System.out.println("TurnTimer elapsed\n"+" ---");
            currentPlayer.setConnected(false);
            insertedDice = false;
            usedCard = false;
            usingTool = 0;
            List list = new ArrayList();
            list.add("EndTurn");
            execute(list);
            if(turnNumber == board.getPlayerList().size()*2){
                board.getRoundTrack().insertDices(board.getDiceSpace().getListDice(),roundManager.getRoundNumber() - 1);
                if(roundManager.getRoundNumber() <=10) {
                    roundManager.startNewRound();
                }
            }
            return;
        } else{             //insertDiceAccepted,draftDiceAccepted,moveDiceAccepted,useToolCardAccepted,UseToolCardError,SwapDiceAccepted
            action.add(string);
            action.add(currentPlayer.getNickname());
        }
        setChanged();
        notifyObservers(action);
    }
}
