package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.RoundManager;
import it.polimi.ingsw.server.model.timer.GameTimer;
import it.polimi.ingsw.server.model.timer.TimedComponent;
import it.polimi.ingsw.server.setUp.TakeDataFile;

import java.util.*;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.*;
import static it.polimi.ingsw.server.costants.MessageConstants.*;
import static it.polimi.ingsw.server.costants.NameCostants.TURN_TIMER;
import static it.polimi.ingsw.server.costants.SetupCostants.CONFIGURATION_FILE;
import static it.polimi.ingsw.server.costants.TimerConstants.TURN_TIMER_PING;

public class Round extends Observable implements TimedComponent {
    private Player firstPlayer;
    private Board board;
    private Player currentPlayer;
    private int turnNumber = 0;
    private List<Player> playersOrder;
    private HashMap<String, State> states;
    private State currentState;
    private Dice pendingDice;
    private Colour movedDiceColour;
    private ToolCard usingTool;
    private List<List<String>> nextActions;
    private List<String> legalActions;
    private RoundManager roundManager;
    private GameMultiplayer game;
    private int favorsDecremented;
    private boolean cardWasUsed;

    private boolean usedCard;
    private boolean draftedDice;
    private boolean bonusInsertDice;

    private GameTimer turnTimer;
    private Timer timer;
    private Long startingTime = 0L;
    private int turnTimerValue;
    private TakeDataFile timerConfig;

    public Round(Player first, Board board, RoundManager roundManager, GameMultiplayer game) {
        timerConfig = new TakeDataFile(CONFIGURATION_FILE);
        turnTimerValue = Integer.parseInt(timerConfig.getParameter(TURN_TIMER));
        usingTool = null;
        this.roundManager = roundManager;
        this.game = game;
        states = new HashMap<>();
        legalActions = new ArrayList<>();
        playersOrder = new ArrayList<>();
        firstPlayer = first;
        this.board = board;
        usedCard = false;
        draftedDice = false;
        bonusInsertDice = false;
        nextActions = new ArrayList<>();
        movedDiceColour = null;
        cardWasUsed = false;
        favorsDecremented = 0;
    }

    /**
     * Creates one instance of each state, extracts the dices for the round and makes the turn's timer start.
     */
    public void roundInit() {
        currentPlayer = firstPlayer;
        states.put(EXTRACT_DICE_STATE, new ExtractDiceState());
        currentState = states.get(EXTRACT_DICE_STATE);
        states.put(INSERT_DICE_STATE, new InsertDiceState());
        states.put(USE_TOOL_CARD_STATE, new UseToolCardState());
        states.put(CANCEL_USE_TOOL_CARD_STATE, new CancelUseToolCardState());
        states.put(MOVE_DICE_STATE, new MoveDiceState());
        states.put(ROLL_DICE_STATE, new RollDiceState());
        states.put(FLIP_DICE_STATE, new FlipDiceState());
        states.put(ROLL_DICE_SPACE_STATE, new RollDiceSpaceState());
        states.put(CHANGE_VALUE_STATE, new ChangeValueState());
        states.put(CHOOSE_VALUE_STATE, new ChooseValueState());
        states.put(DRAFT_DICE_STATE, new DraftDiceState());
        states.put(PLACE_DICE_STATE, new PlaceDiceState());
        states.put(PLACE_DICE_SPACE_STATE, new PlaceDiceSpaceState());
        states.put(SWAP_DICE_STATE, new SwapDiceState());
        states.put(SWAP_DICE_BAG_STATE, new SwapDiceBagState());
        states.put(END_TURN_STATE, new EndTurnState());
        setPlayersOrder();

        Log.getLogger().addLog("new round started\n" + " ---",Level.INFO,this.getClass().getName(),"roundInit");
        currentState.execute(this, null);
        notifyChanges(START_ROUND);
        notifyChanges(START_TURN);
        notifyChanges(SET_ACTIONS);

        startingTime = System.currentTimeMillis();
        turnTimer = new GameTimer(turnTimerValue, this);
        timer = new Timer();
        timer.schedule(turnTimer, 0L, 5000L);
    }

    /**
     * Changes the current state to the specified one and then execute it. Resets the turn's timer.
     * @param action contains the next state and eventually parameters for it
     */
    public void execute(List action) {
        if (legalActions.contains(action.get(0))) {
            timer.cancel();
            currentState = states.get(currentState.nextState(action));
            currentState.execute(this, action);
            if (currentState.toString().equals(END_TURN_STATE))
                notifyChanges(START_TURN);
            notifyChanges(SET_ACTIONS);
            startingTime = System.currentTimeMillis();
            turnTimer = new GameTimer(turnTimerValue, this);
            timer = new Timer();
            timer.schedule(turnTimer, 0L, 5000L);
        } else {
            Log.getLogger().addLog("can't perform: " + action.get(0) + " now\n" + " ---", Level.INFO,this.getClass().getName(),"execute");
            notifyChanges(SET_ACTIONS);
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    void incrementTurnNumber() {
        turnNumber++;
    }

    public List<Player> getPlayersOrder() {
        return playersOrder;
    }

    Board getBoard() {
        return board;
    }

    RoundManager getRoundManager() {
        return roundManager;
    }

    GameMultiplayer getGame(){ return game; }

    public Dice getPendingDice() {
        return pendingDice;
    }

    public void setPendingDice(Dice dice) {
        this.pendingDice = dice;
    }

    int getFavorsDecremented() {
        return favorsDecremented;
    }

    public void setFavorsDecremented(int favorsDecremented) {
        this.favorsDecremented = favorsDecremented;
    }

    public boolean getCardWasUsed() {
        return cardWasUsed;
    }

    void setCardWasUsed(boolean cardWasUsed) {
        this.cardWasUsed = cardWasUsed;
    }

    public String getCurrentState() {
        return currentState.toString();
    }

    public void setUsingTool(ToolCard using) {
        this.usingTool = using;
    }

    public ToolCard getUsingTool() {
        return usingTool;
    }

    void setLegalActions(List<String> legalActions) {
        this.legalActions = legalActions;
    }

    boolean isUsedCard() {
        return usedCard;
    }

    void setUsedCard(boolean usedCard) {
        this.usedCard = usedCard;
    }

    public boolean isDraftedDice() {
        return draftedDice;
    }

    void setDraftedDice(boolean draftedDice) {
        this.draftedDice = draftedDice;
    }

    boolean hasBonusInsertDice() {
        return bonusInsertDice;
    }

    void setBonusInsertDice(boolean bonusInsertDice) {
        this.bonusInsertDice = bonusInsertDice;
    }

    public void setNextActions(List<List<String>> nextActions) {
        this.nextActions = nextActions;
    }

    List<List<String>> getNextActions() {
        return nextActions;
    }

    Colour getMovedDiceColour() {
        return movedDiceColour;
    }

    void setMovedDiceColour(Colour movedDiceColour) {
        this.movedDiceColour = movedDiceColour;
    }

    /**
     * Calculates the players' turn order of the round.
     */
    private void setPlayersOrder() {
        int playerIndex = board.getIndex(firstPlayer);
        playersOrder.add(firstPlayer);
        for (int i = 1; i < board.numPlayers() * 2; i++) {
            if (i < board.numPlayers())
                if (playerIndex == board.numPlayers() - 1)
                    playerIndex = 0;
                else
                    playerIndex++;
            else if (i > board.numPlayers()) {
                if (playerIndex == 0)
                    playerIndex = board.numPlayers() - 1;
                else
                    playerIndex--;
            }
            playersOrder.add(board.getPlayer(playerIndex));
        }
    }

    /**
     * Disconnects the current player.
     */
    public void timerElapsed() {
        Log.getLogger().addLog("TurnTimer elapsed\n" + " ---",Level.INFO,this.getClass().getName(),"TimerElapsed");
        disconnectPlayer();
    }

    /**
     * Notifies the ping of the timer to the players.
     */
    public void timerPing(){ notifyChanges(TIMER_PING); }

    /**
     * Sets the current player as disconnected and makes the turn end.
     * If the turn was the last one of the game, makes the game end.
     */
    public void disconnectPlayer(){
        Log.getLogger().addLog(currentPlayer.getNickname() + " disconnected:\n" + "players still connected: " + game.getBoard().getConnected() + "\n ---",Level.INFO,this.getClass().getName(),"DisconnectPlayer");
        currentPlayer.setConnected(false);
        notifyChanges(LOGOUT);
        draftedDice = false;
        usedCard = false;
        usingTool = null;
        if(board.getConnected() == 1) {
            game.endGame(currentPlayer);
            legalActions.clear();
            return;
        }
        if (pendingDice != null) {
            board.getDiceSpace().insertDice(pendingDice);
            pendingDice = null;
        }
        if (turnNumber == board.getPlayerList().size() * 2 - 1) {
            if (roundManager.getRoundNumber() <= 10) {
                board.getRoundTrack().insertDices(board.getDiceSpace().getListDice(), roundManager.getRoundNumber() - 1);
                roundManager.startNewRound();
            }else
                game.endGame(currentPlayer);
        } else {
            List<String> action = new ArrayList<>();
            action.add(END_TURN);
            execute(action);
            notifyChanges(START_TURN);
            notifyChanges(SET_ACTIONS);
        }
    }

    /**
     * Notifies different changes to the observer.
     * @param string head of the message that will be sent to the observer
     */
    public void notifyChanges(String string) {
        List action = new ArrayList<>();
        switch (string) {
            case START_ROUND:
                action.add(string);
                break;
            case SET_ACTIONS:
                action.add(string);
                action.add(currentPlayer.getNickname());
                action.addAll(legalActions);
                break;
            case ROLL_DICE_ACCEPTED:
            case FLIP_DICE_ACCEPTED:
                action.add(string);
                action.add(currentPlayer.getNickname());
                action.add(pendingDice.getValue());
                break;
            case SWAP_DICE_BAG_ACCEPTED:
                action.add(string);
                action.add(currentPlayer.getNickname());
                action.add(pendingDice.getColour().toString());
                action.add(pendingDice.getValue());
                break;
            case USE_TOOL_CARD_ACCEPTED:
            case CANCEL_USE_TOOL_CARD_ACCEPTED:
                action.add(string);
                action.add(currentPlayer.getNickname());
                action.add(currentPlayer.getFavour());
                break;
            case TIMER_PING:
                action.add(TURN_TIMER_PING);
                action.add(currentPlayer.getNickname());
                action.add(turnTimerValue - (System.currentTimeMillis() - startingTime) / 1000);
                break;
            default:
                // startTurn,insertDiceAccepted,draftDiceAccepted,moveDiceAccepted,useToolCardError,swapDiceAccepted,
                // changeValueAccepted,rollDiceSpaceAccepted,placeDiceSpaceAccepted,flipDiceAccepted,
                // chooseValueAccepted,chooseValueError,moveDiveError,logout
                action.add(string);
                action.add(currentPlayer.getNickname());
                break;
        }
        setChanged();
        notifyObservers(action);
    }
}
