package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import it.polimi.ingsw.server.timer.GameTimer;
import it.polimi.ingsw.server.timer.TimedComponent;

import java.util.*;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameCreationMessages.START_TURN;
import static it.polimi.ingsw.costants.LoginMessages.TIMER_PING;
import static it.polimi.ingsw.costants.TimerCostants.TURN_TIMER_VALUE;
import static it.polimi.ingsw.costants.TimerCostants.TURN_TIMER_PING;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

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
    private int favorsDecremented;
    private boolean cardWasUsed;

    private boolean usedCard;
    private boolean insertedDice;
    private boolean bonusInsertDice;

    private GameTimer turnTimer;
    private Timer timer;
    private Long startingTime = 0L;


    public Round(Player first, Board board, RoundManager roundManager) {
        usingTool = null;
        this.roundManager = roundManager;
        states = new HashMap<>();
        legalActions = new ArrayList<>();
        playersOrder = new ArrayList<>();
        firstPlayer = first;
        this.board = board;
        usedCard = false;
        insertedDice = false;
        bonusInsertDice = false;
        nextActions = new ArrayList<>();
        movedDiceColour = null;
        cardWasUsed = false;
        favorsDecremented = 0;
    }

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

        System.out.println("new round started\n" + " ---");
        currentState.execute(this, null);
        notifyChanges(START_ROUND);
        notifyChanges(START_TURN);
        notifyChanges(SET_ACTIONS);

        startingTime = System.currentTimeMillis();
        turnTimer = new GameTimer(TURN_TIMER_VALUE, this);
        timer = new Timer();
        timer.schedule(turnTimer, 0L, 5000L);
    }

    public void execute(List action) {
        if (legalActions.contains(action.get(0))) {
            timer.cancel();
            currentState = states.get(currentState.nextState(action));
            currentState.execute(this, action);
            if (currentState.toString().equals(END_TURN_STATE))
                notifyChanges(START_TURN);
            notifyChanges(SET_ACTIONS);
            startingTime = System.currentTimeMillis();
            turnTimer = new GameTimer(TURN_TIMER_VALUE, this);
            timer = new Timer();
            timer.schedule(turnTimer, 0L, 5000L);
        } else {
            System.out.println("can't perform: " + action.get(0) + " now\n" + " ---");
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

    List<Player> getPlayersOrder() {
        return playersOrder;
    }

    public Board getBoard() {
        return board;
    }

    RoundManager getRoundManager() {
        return roundManager;
    }

    Dice getPendingDice() {
        return pendingDice;
    }

    void setPendingDice(Dice dice) {
        this.pendingDice = dice;
    }

    int getFavorsDecremented() {
        return favorsDecremented;
    }

    void setFavorsDecremented(int favorsDecremented) {
        this.favorsDecremented = favorsDecremented;
    }

    boolean getCardWasUsed() {
        return cardWasUsed;
    }

    void setCardWasUsed(boolean cardWasUsed) {
        this.cardWasUsed = cardWasUsed;
    }

    public String getCurrentState() {
        return currentState.toString();
    }

    void setUsingTool(ToolCard using) {
        this.usingTool = using;
    }

    ToolCard getUsingTool() {
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

    boolean isInsertedDice() {
        return insertedDice;
    }

    void setInsertedDice(boolean insertedDice) {
        this.insertedDice = insertedDice;
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

    public List<List<String>> getNextActions() {
        return nextActions;
    }

    Colour getMovedDiceColour() {
        return movedDiceColour;
    }

    void setMovedDiceColour(Colour movedDiceColour) {
        this.movedDiceColour = movedDiceColour;
    }

    private void setPlayersOrder() {
        int playerIndex = board.getIndex(firstPlayer);
        playersOrder.add(firstPlayer);
        for (int i = 1; i < board.numPlayers() * 2; i++) {
            if (i < getBoard().numPlayers())
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

    public void timerElapsed() {
        System.out.println("TurnTimer elapsed\n" + " ---");
        currentPlayer.setConnected(false);
        insertedDice = false;
        usedCard = false;
        usingTool = null;
        if (pendingDice != null) {
            board.getDiceSpace().insertDice(pendingDice);
            pendingDice = null;
        }
        if (turnNumber == board.getPlayerList().size() * 2 - 1) {
            if (roundManager.getRoundNumber() <= 10) {
                board.getRoundTrack().insertDices(board.getDiceSpace().getListDice(), roundManager.getRoundNumber() - 1);
                roundManager.startNewRound();
            }
        } else {
            List<String> action = new ArrayList<>();
            action.add(END_TURN);
            execute(action);
        }
    }

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
                action.add(TURN_TIMER_VALUE - (System.currentTimeMillis() - startingTime) / 1000);
                break;
            default:
                // startTurn,insertDiceAccepted,draftDiceAccepted,moveDiceAccepted,useToolCardError,swapDiceAccepted,
                // changeValueAccepted,rollDiceSpaceAccepted,placeDiceSpaceAccepted,
                // flipDiceAccepted,chooseValueAccepted,chooseValueError,moveDiveError
                action.add(string);
                action.add(currentPlayer.getNickname());
                break;
        }
        setChanged();
        notifyObservers(action);
    }


}
