package it.polimi.ingsw.Model.game.states;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.DiceSpace;
import it.polimi.ingsw.Model.Player;

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

    public void setReference(Round r){
        this.currentRound= r;
    }
    public void startRound(){
        currentState.setActions(currentRound);
    }

    //al momento changeState viene chiamato in automatico dagli stati ma probabilmente verrà chiamato dal controller su azione dello user eseguita

    /*alcuni stati come InitialState possono condurre a stati successivi diversi(PickDiceState,UseCardState,EndTurnState) in base all'azione
    selezionata dall'utente, se change state verrà chiamato dal controller ad azione dell'utente eseguita, potrò associare tale azione
    ad una stringa corrispondente ad una key di states che passerà a changeState che a sua volta passerà a nextState dello stato corrente, il quale
    condurrà allo stato successivo esatto in base alla stringa passata
     */
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

    public DiceSpace getDices() { return dices; }

    public void setDices(DiceSpace dices) { this.dices = dices; }

    public Dice getPendingDice() { return pendingDice; }

    public void setPendingDice(Dice dice){this.pendingDice = dice;}

    public String getCurrentState(){ return currentState.toString();}
}
