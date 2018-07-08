package it.polimi.ingsw.client.clientConnection.socket;

import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.setUp.TakeDataFile;
import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.client.view.Message;
import it.polimi.ingsw.client.view.TypeMessage;
import it.polimi.ingsw.client.view.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.client.constants.NameConstants.SERVER_IP;
import static it.polimi.ingsw.client.constants.NameConstants.SOCKET_PORT;
import static it.polimi.ingsw.client.constants.MessageConstants.*;
import static it.polimi.ingsw.client.constants.printCostants.SERVER_CONNECTION_ERROR;
import static it.polimi.ingsw.client.constants.printCostants.THREAD_ERROR;
import static it.polimi.ingsw.server.costants.TimerConstants.TURN_TIMER_PING;

public class SocketConnection implements Connection,Runnable {

    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    private Handler hand;  // used to manage graphic
    private boolean stopThread = false;
    private String host;
    private int port;
    private boolean inGame;

    public SocketConnection(Handler hand) throws IOException {
        setConnection();
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream());
        in = new Scanner(socket.getInputStream());
        this.hand = hand;
        inGame = false;
    }

    /**
     * sets the connection
     */
    private void setConnection() throws IOException {
        TakeDataFile netConfig = new TakeDataFile();

        host = netConfig.getParameter(SERVER_IP);
        port = Integer.parseInt(netConfig.getParameter(SOCKET_PORT));
    }


    /**
     * @param str is scheme's name
     */
    public void sendSchema(String str) {
        String action = CHOOSE_SCHEMA+"-";
        action += hand.getView().getName() + "-";
        action += str;
        out.println(action);
        out.flush();
    }


    /**
     * log the player
     * @param nickname is the name of player
     */
    public void login(String nickname) {
        inGame = true;
        out.println(LOGIN +"-"+ nickname);
        out.flush();
    }

    /**
     * disconnect the player
     */
    public void disconnect() {
        inGame = false;
        stopRunning();
        out.println(DISCONNECTED);
        out.flush();
        out.close();
        try {
           socket.close();
        } catch (IOException e) {
            Message.println(e.getMessage(),TypeMessage.ERROR_MESSAGE);
        }
        in.close();
    }


    /**
     * used to insert dice to scheme from diceSpace
     * @param indexDiceSpace is index of dice space
     * @param row is index of row
     * @param column is index of column
     */
    public void insertDice(int indexDiceSpace, int row, int column) {
        out.println(PICK_DICE + "-" + indexDiceSpace + "-" + row + "-" + column);
        out.flush();
    }

    /**
     * invoked when you want use tool card
     * @param toolNumber is tool card's number
     */
    public void useToolCard(int toolNumber) {
        out.println(USE_TOOL_CARD + "-" + toolNumber);
        out.flush();
    }

    /**
     * invoked when you want move dice in scheme
     * @param oldRow is the row from take dice
     * @param oldColumn is the column from take dice
     * @param newRow is the row to move dice
     * @param newColumn is the column to move dice
     */
    public void moveDice(int oldRow, int oldColumn, int newRow, int newColumn) {
        out.println(MOVE_DICE + "-" + oldRow + "-" + oldColumn + "-" + newRow + "-" + newColumn);
        out.flush();
    }


    /**
     * is invoked when use draft dice
     * @param indexDiceSpace is index of dice space
     */
    public void sendDraft(int indexDiceSpace) {
        out.println(DRAFT_DICE + "-" + indexDiceSpace);
        out.flush();
    }

    /**
     * is invoked when use place dice
     * @param row is row index of scheme
     * @param column is column index of scheme
     */
    public void sendPlaceDice(int row, int column) {
        out.println(PLACE_DICE + "-" + row + "-" + column);
        out.flush();
    }

    /**
     * is invoked when use changeValue
     * @param change is "decrement" or "increment"
     */
    public void changeValue(String change) {
        out.println(CHANGE_VALUE + "-" + change);
        out.flush();
    }

    /**
     * is invoked when use roll dice
     */
    public void rollDice() {
        out.println(ROLL_DICE);
        out.flush();
    }

    /**
     * take a dice from round track
     * @param numRound is the number of round
     * @param indexDice is index of dice
     */
    public void swapDice(int numRound, int indexDice) {
        out.println(SWAP_DICE + "-" + numRound + "-" + indexDice);
        out.flush();
    }

    /**
     * invoked when use cancel tool card
     */
    public void cancelUseToolCard() {
        out.println(CANCEL_USE_TOOL_CARD);
        out.flush();
    }

    /**
     * send end turn message
     */
    public void sendEndTurn() {
        out.println(END_TURN);
        out.flush();
    }

    /**
     * turn to opposite face of dice
     */
    public void flipDice() {
        out.println(FLIP_DICE);
        out.flush();
    }

    /**
     * place dices in dice space
     */
    public void placeDiceSpace() {
        out.println(PLACE_DICE_SPACE);
        out.flush();
    }

    /**
     * roll dices in dice space
     */
    public void rollDiceSpace() {
        out.println(ROLL_DICE_SPACE);
        out.flush();
    }

    /**
     * exchange dice with dice bag
     */
    public void swapDiceBag() {
        out.println(SWAP_DICE_BAG);
        out.flush();
    }

    /**
     * is invoked when choose value of dice
     * @param chooseValue is the new value of dice
     */
    public void chooseValue(int chooseValue) {
        out.println(CHOOSE_VALUE + "-" + chooseValue);
        out.flush();
    }

    /**
     * send custom scheme to server
     * @param schema is the name of custom schema
     */
    public void sendCustomSchema(String schema) {
        out.println(CUSTOM_SCHEMA + "-" + hand.getView().getName() + "-" + schema);
        out.flush();
    }

    /**
     * stops receiving messages
     */
    private void stopRunning() {
        stopThread = true;
    }

    /**
     * starts receiving messages
     */
    public void run() {
        while (!stopThread) {
            try {
                String str = in.nextLine();
                List<String> action = new ArrayList<>();
                StringTokenizer token = new StringTokenizer(str, "-");
                while (token.hasMoreTokens())
                    action.add(token.nextToken());
                deliverGI(action);
            } catch (NoSuchElementException e) {
                stopThread = true;
                if(inGame) {
                    Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        }
    }

    /**
     * deliver action on GUI or CLI
     * @param action is the player's action
     */
    private void deliverGI(List<String> action) {
        View v = hand.getView();
        List<String> players;
        List<String> schemas;
        List<Integer> scores;
        List<String> colours;
        List<Integer> values;

        switch (action.get(0)) {
            case LOGIN_SUCCESSFUL:
                if (action.get(1).equals(v.getName())) {
                    v.login(action.get(0));
                    v.setNumberPlayer(Integer.parseInt(action.get(2)));
                } else
                    v.playerConnected(action.get(1));
                break;
            case LOGIN_ERROR:
                v.login(action.get(0) + "-" + action.get(1));
                break;
            case LOGOUT:
                v.playerDisconnected(action.get(1));
                break;
            case TIMER_PING:
                v.timerPing(action.get(1));
                break;
            case TURN_TIMER_PING:
                v.turnTimerPing(Integer.parseInt(action.get(1)));
                break;
            case START_GAME:
                v.createGame();
                break;
            case SET_SCHEMAS:
                v.setSchemas(action.subList(1, 5));
                break;
            case SET_PRIVATE_CARD:
                v.setPrivateCard(action.get(1));
                break;
            case SET_PUBLIC_OBJECTIVES:
                v.setPublicObjectives(action.subList(1, 4));
                break;
            case SET_TOOL_CARDS:
                action.remove(0);
                List<Integer> toolCards = action.stream().map(Integer::parseInt).collect(Collectors.toList());
                v.setToolCards(toolCards);
                break;
            case APPROVED_SCHEMA:
                v.chooseSchema(action.get(1));
                break;
            case SET_OPPONENTS_SCHEMAS:
                v.setOpponentsSchemas(action.subList(1, action.size()));
                break;
            case START_TURN:
                v.startTurn(action.get(1));
                break;
            case START_ROUND:
                v.startRound();
                break;
            case SET_ACTIONS:
                v.setActions(action.subList(1, action.size()));
                break;
            case SET_DICE_SPACE:
                colours = new ArrayList<>();
                values = new ArrayList<>();
                for (int i = 1; i < action.size(); i += 2) {
                    colours.add(action.get(i));
                    values.add(Integer.parseInt(action.get(i + 1)));
                }
                v.setDiceSpace(colours, values);
                break;
            case INSERT_DICE_ACCEPTED:
                v.insertDiceAccepted();
                break;
            case PICK_DICE_SPACE:
                try {
                    v.pickDiceSpace(Integer.parseInt(action.get(1)));
                } catch (InterruptedException e) {
                    Message.println(THREAD_ERROR,TypeMessage.ERROR_MESSAGE);
                    Thread.currentThread().interrupt();
                }
                break;
            case PICK_DICE_SPACE_ERROR:
                v.pickDiceSpaceError();
                break;
            case PLACE_DICE_SCHEMA_ERROR:
                v.placeDiceSchemaError();
                break;
            case PLACE_DICE_SCHEMA:
                v.placeDiceSchema(action.get(1), Integer.parseInt(action.get(2)), Integer.parseInt(action.get(3)), action.get(4), Integer.parseInt(action.get(5)));
                break;
            case USE_TOOL_CARD_ACCEPTED:
                v.useToolCardAccepted(Integer.parseInt(action.get(1)));
                break;
            case USE_TOOL_CARD_ERROR:
                v.useToolCardError();
                break;
            case DRAFT_DICE_ACCEPTED:
                v.draftDiceAccepted();
                break;
            case MOVE_DICE_ACCEPTED:
                v.moveDiceAccepted();
                break;
            case MOVE_DICE_ERROR:
                v.moveDiceError();
                break;
            case PICK_DICE_SCHEMA_ERROR:
                v.pickDiceSchemaError();
                break;
            case PICK_DICE_SCHEMA:
                v.pickDiceSchema(action.get(1), Integer.parseInt(action.get(2)), Integer.parseInt(action.get(3)));
                break;
            case PLACE_DICE_ACCEPTED:
                v.placeDiceAccepted();
                break;
            case CHANGE_VALUE_ACCEPTED:
                v.changeValueAccepted();
                break;
            case CHANGE_VALUE_ERROR:
                v.changeValueError();
                break;
            case ROLL_DICE_ACCEPTED:
                v.rollDiceAccepted(Integer.parseInt(action.get(1)));
                break;
            case PLACE_DICE_ROUND_TRACK:
                colours = new ArrayList<>();
                values = new ArrayList<>();
                for (int i = 2; i < action.size(); i += 2) {
                    colours.add(action.get(i));
                    values.add(Integer.parseInt(action.get(i + 1)));
                }
                v.placeDiceRoundTrack(Integer.parseInt(action.get(1)), colours, values);
                break;
            case PICK_DICE_ROUND_TRACK_ERROR:
                v.pickDiceRoundTrackError();
                break;
            case PICK_DICE_ROUND_TRACK:
                v.pickDiceRoundTrack(Integer.parseInt(action.get(1)), Integer.parseInt(action.get(2)));
                break;
            case SWAP_DICE_ACCEPTED:
                v.swapDiceAccepted();
                break;
            case CANCEL_USE_TOOL_CARD_ACCEPTED:
                v.cancelUseToolCardAccepted(Integer.parseInt(action.get(1)));
                break;
            case FLIP_DICE_ACCEPTED:
                v.flipDiceAccepted(Integer.parseInt(action.get(1)));
                break;
            case PLACE_DICE_DICESPACE:
                v.placeDiceSpace(action.get(1), Integer.parseInt(action.get(2)));
                break;
            case PLACE_DICE_SPACE_ACCEPTED:
                v.placeDiceSpaceAccepted();
                break;
            case ROLL_DICE_SPACE_ACCEPTED:
                v.rollDiceSpaceAccepted();
                break;
            case SWAP_DICE_BAG_ACCEPTED:
                v.swapDiceBagAccepted(action.get(1), Integer.parseInt(action.get(2)));
                break;
            case CHOOSE_VALUE_ACCEPTED:
                v.chooseValueAccepted();
                break;
            case CHOOSE_VALUE_ERROR:
                v.chooseValueError();
                break;
            case APPROVED_SCHEMA_CUSTOM:
                v.schemaCustomAccepted(action.get(1));
                break;
            case SET_OPPONENTS_CUSTOM_SCHEMAS:
                v.setOpponentsCustomSchemas(action.subList(1, action.size()));
                break;
            case SET_WINNER:
                v.setWinner(action.get(1));
                break;
            case SET_RANKINGS:
                players = new ArrayList<>();
                scores = new ArrayList<>();
                for (int i = 1; i < action.size(); i += 2) {
                    players.add(action.get(i));
                    scores.add(Integer.parseInt(action.get(i + 1)));
                }
                v.setRankings(players, scores);
                break;
            case SET_SCHEMAS_ON_RECONNECT:
                players = new ArrayList<>();
                schemas = new ArrayList<>();
                for (int i = 1; i < action.size(); i += 2) {
                    players.add(action.get(i));
                    schemas.add(action.get(i + 1));
                }
                v.setSchemasOnReconnect(players, schemas);
                break;
            default:
                break;
        }
    }
}
