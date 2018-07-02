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
import static it.polimi.ingsw.client.constants.SetupConstants.CONFIGURATION_FILE;
import static it.polimi.ingsw.client.constants.MessageConstants.*;

public class SocketConnection implements Connection,Runnable {

    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    private Handler hand;  // used to manage graphic
    private boolean stopThread = false;
    private String host;
    private int port;

    public SocketConnection(Handler hand) throws IOException {
        setConnection();
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream());
        in = new Scanner(socket.getInputStream());
        this.hand = hand;
    }

    private void setConnection() throws IOException {
        TakeDataFile netConfig = new TakeDataFile(CONFIGURATION_FILE);

            host = netConfig.getParameter(SERVER_IP);
            port = Integer.parseInt(netConfig.getParameter(SOCKET_PORT));
    }

    public void sendSchema(String str) {
        String action = CHOOSE_SCHEMA+"-";
        action += hand.getView().getName() + "-";
        action += str;
        out.println(action);
        out.flush();
    }


    public void login(String nickname) {
        out.println(LOGIN +"-"+ nickname);
        out.flush();
    }

    public void disconnect() {
        stopRunning();
        out.println(DISCONNECTED);
        out.flush();
        out.close();
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        in.close();
    }

    public void insertDice(int indexDiceSpace, int row, int column) {
        out.println(PICK_DICE + "-" + indexDiceSpace + "-" + row + "-" + column);
        out.flush();
    }

    public void useToolCard(int toolNumber) {
        out.println(USE_TOOL_CARD + "-" + toolNumber);
        out.flush();
    }

    public void moveDice(int oldRow, int oldColumn, int newRow, int newColumn) {
        out.println(MOVE_DICE + "-" + oldRow + "-" + oldColumn + "-" + newRow + "-" + newColumn);
        out.flush();
    }

    public void sendDraft(int indexDiceSpace) {
        out.println(DRAFT_DICE + "-" + indexDiceSpace);
        out.flush();
    }

    public void sendPlaceDice(int row, int column) {
        out.println(PLACE_DICE + "-" + row + "-" + column);
        out.flush();
    }

    public void changeValue(String change) {
        out.println(CHANGE_VALUE + "-" + change);
        out.flush();
    }

    public void rollDice() {
        out.println(ROLL_DICE);
        out.flush();
    }

    public void swapDice(int numRound, int indexDice) {
        out.println(SWAP_DICE + "-" + numRound + "-" + indexDice);
        out.flush();
    }

    public void cancelUseToolCard() {
        out.println(CANCEL_USE_TOOL_CARD);
        out.flush();
    }

    public void sendEndTurn() {
        out.println(END_TURN);
        out.flush();
    }

    public void flipDice() {
        out.println(FLIP_DICE);
        out.flush();
    }

    public void placeDiceSpace() {
        out.println(PLACE_DICE_SPACE);
        out.flush();
    }

    public void rollDiceSpace() {
        out.println(ROLL_DICE_SPACE);
        out.flush();
    }

    public void swapDiceBag() {
        out.println(SWAP_DICE_BAG);
        out.flush();
    }

    public void chooseValue(int chooseValue) {
        out.println(CHOOSE_VALUE + "-" + chooseValue);
        out.flush();
    }

    public void sendCustomSchema(String schema) {
        out.println(CUSTOM_SCHEMA + "-" + hand.getView().getName() + "-" + schema);
        out.flush();
    }

    public void stopRunning() {
        stopThread = true;
    }


    public void run() {
        while (!stopThread) {
            try {
                String str = in.nextLine();
                List<String> action = new ArrayList<String>();
                StringTokenizer token = new StringTokenizer(str, "-");
                while (token.hasMoreTokens())
                    action.add(token.nextToken());
                deliverGI(action);
            } catch (NoSuchElementException e) {
                Message.print("Errore di collegamento con il server", TypeMessage.ERROR_MESSAGE);
                stopThread = true;
                System.exit(1);
            }
        }
    }

    // deliver action on GUI or CLI
    private void deliverGI(List<String> action) {
        View v = hand.getView();
        if (action.get(0).equals(LOGIN_SUCCESSFUL)) {
            if (action.get(1).equals(v.getName())) {
                v.login(action.get(0));
                v.setNumberPlayer(Integer.parseInt(action.get(2)));
            } else
                v.playerConnected(action.get(1));
        } else if (action.get(0).equals(LOGIN_ERROR)) {
            v.login(action.get(0) + "-" + action.get(1));
        } else if (action.get(0).equals(LOGOUT)) {
            v.playerDisconnected(action.get(1));
        } else if (action.get(0).equals(TIMER_PING)) {
            v.timerPing(action.get(1));
        } else if (action.get(0).equals(START_GAME)) {
            v.createGame();
        } else if (action.get(0).equals(SET_SCHEMAS)) {
            v.setSchemas(action.subList(1, 5));
        } else if (action.get(0).equals(SET_PRIVATE_CARD)) {
            v.setPrivateCard(action.get(1));
        } else if (action.get(0).equals(SET_PUBLIC_OBJECTIVES)) {
            v.setPublicObjectives(action.subList(1, 4));
        } else if (action.get(0).equals(SET_TOOL_CARDS)) {
            action.remove(0);
            List<Integer> toolCards = action.stream().map(Integer::parseInt).collect(Collectors.toList());
            v.setToolCards(toolCards);
        } else if (action.get(0).equals(APPROVED_SCHEMA)) {
            v.chooseSchema(action.get(1));
        } else if (action.get(0).equals(SET_OPPONENTS_SCHEMAS)) {
            v.setOpponentsSchemas(action.subList(1, action.size()));
        } else if (action.get(0).equals(START_TURN)) {
            v.startTurn(action.get(1));
        } else if (action.get(0).equals(START_ROUND)) {
            v.startRound();
        } else if (action.get(0).equals(SET_ACTIONS)) {
            v.setActions(action.subList(1, action.size()));
        } else if (action.get(0).equals(SET_DICE_SPACE)) {
            List<String> colours= new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            for(int i=1; i<action.size(); i+=2){
                colours.add(action.get(i));
                values.add(Integer.parseInt(action.get(i+1)));
            }
            v.setDiceSpace(colours, values);
        } else if (action.get(0).equals(INSERT_DICE_ACCEPTED)) {
            v.insertDiceAccepted();
        } else if (action.get(0).equals(PICK_DICE_SPACE)) {
            try {
                v.pickDiceSpace(Integer.parseInt(action.get(1)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (action.get(0).equals(PICK_DICE_SPACE_ERROR)) {
            v.pickDiceSpaceError();
        } else if (action.get(0).equals(PLACE_DICE_SCHEMA_ERROR)) {
            v.placeDiceSchemaError();
        } else if (action.get(0).equals(PLACE_DICE_SCHEMA)) {
            v.placeDiceSchema(action.get(1),Integer.parseInt(action.get(2)),Integer.parseInt(action.get(3)),action.get(4),Integer.parseInt(action.get(5)));
        } else if (action.get(0).equals(USE_TOOL_CARD_ACCEPTED)) {
            v.useToolCardAccepted(Integer.parseInt(action.get(1)));
        } else if (action.get(0).equals(USE_TOOL_CARD_ERROR)) {
            v.useToolCardError();
        } else if (action.get(0).equals(DRAFT_DICE_ACCEPTED)) {
            v.draftDiceAccepted();
        } else if (action.get(0).equals(MOVE_DICE_ACCEPTED)) {
            v.moveDiceAccepted();
        } else if (action.get(0).equals(MOVE_DICE_ERROR)) {
            v.moveDiceError();
        } else if (action.get(0).equals(PICK_DICE_SCHEMA_ERROR)) {
            v.pickDiceSchemaError();
        } else if (action.get(0).equals(PICK_DICE_SCHEMA)) {
            v.pickDiceSchema(action.get(1),Integer.parseInt(action.get(2)),Integer.parseInt(action.get(3)));
        } else if (action.get(0).equals(PLACE_DICE_ACCEPTED)) {
            v.placeDiceAccepted();
        } else if (action.get(0).equals(CHANGE_VALUE_ACCEPTED)) {
            v.changeValueAccepted();
        } else if (action.get(0).equals(CHANGE_VALUE_ERROR)) {
            v.changeValueError();
        } else if (action.get(0).equals(ROLL_DICE_ACCEPTED)) {
            v.rollDiceAccepted(Integer.parseInt(action.get(1)));
        } else if (action.get(0).equals(PLACE_DICE_ROUND_TRACK)) {
            List<String> colours= new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            for(int i=2; i<action.size(); i+=2){
                colours.add(action.get(i));
                values.add(Integer.parseInt(action.get(i+1)));
            }
            v.placeDiceRoundTrack(Integer.parseInt(action.get(1)), colours, values);
        } else if (action.get(0).equals(PICK_DICE_ROUND_TRACK_ERROR)) {
            v.pickDiceRoundTrackError();
        } else if (action.get(0).equals(PICK_DICE_ROUND_TRACK)) {
            v.pickDiceRoundTrack(Integer.parseInt(action.get(1)),Integer.parseInt(action.get(2)));
        } else if (action.get(0).equals(SWAP_DICE_ACCEPTED)) {
            v.swapDiceAccepted();
        } else if (action.get(0).equals(CANCEL_USE_TOOL_CARD_ACCEPTED)) {
            v.cancelUseToolCardAccepted(Integer.parseInt(action.get(1)));
        } else if (action.get(0).equals(FLIP_DICE_ACCEPTED)) {
            v.flipDiceAccepted(Integer.parseInt(action.get(1)));
        } else if (action.get(0).equals(PLACE_DICE_DICESPACE)) {
            v.placeDiceSpace(action.get(1),Integer.parseInt(action.get(2)));
        } else if (action.get(0).equals(PLACE_DICE_SPACE_ACCEPTED)) {
            v.placeDiceSpaceAccepted();
        } else if (action.get(0).equals(ROLL_DICE_SPACE_ACCEPTED)) {
            v.rollDiceSpaceAccepted();
        } else if (action.get(0).equals(SWAP_DICE_BAG_ACCEPTED)) {
            v.swapDiceBagAccepted(action.get(1),Integer.parseInt(action.get(2)));
        } else if (action.get(0).equals(CHOOSE_VALUE_ACCEPTED)) {
            v.chooseValueAccepted();
        } else if (action.get(0).equals(CHOOSE_VALUE_ERROR)) {
            v.chooseValueError();
        } else if (action.get(0).equals(APPROVED_SCHEMA_CUSTOM)) {
            v.schemaCustomAccepted(action.get(1));
        } else if (action.get(0).equals(SET_OPPONENTS_CUSTOM_SCHEMAS)) {
            v.setOpponentsCustomSchemas(action.subList(1, action.size()));
        }else if (action.get(0).equals(SET_WINNER)) {
            v.setWinner(action.get(1));
        }else if (action.get(0).equals(SET_RANKINGS)) {
            List<String> players = new ArrayList<>();
            List<Integer> scores = new ArrayList<>();
            for(int i=1; i<action.size(); i+=2){
                players.add(action.get(i));
                scores.add(Integer.parseInt(action.get(i+1)));
            }
            v.setRankings(players,scores);
        }else if (action.get(0).equals(SET_SCHEMAS_ON_RECONNECT)) {
            List<String> players = new ArrayList<>();
            List<String> schemas = new ArrayList<>();
            for(int i=1; i<action.size(); i+=2){
                players.add(action.get(i));
                schemas.add(action.get(i+1));
            }
            v.setSchemasOnReconnect(players,schemas);
        }
    }
}
