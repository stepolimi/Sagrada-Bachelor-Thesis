package it.polimi.ingsw.server.connection.socket;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.connection.Connected;
import it.polimi.ingsw.server.connection.Connection;
import it.polimi.ingsw.server.virtual.view.VirtualView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.CONNECTING_WITH_SOCKET;
import static it.polimi.ingsw.server.costants.LogConstants.SOCKET_CONNECTION_LOGIN;
import static it.polimi.ingsw.server.costants.LogConstants.SOCKET_CONNECTION_LOGOUT;
import static it.polimi.ingsw.server.costants.MessageConstants.*;
import static it.polimi.ingsw.server.costants.TimerConstants.TURN_TIMER_PING;

public class SocketConnection implements Runnable,Connection {
    private final Socket s;
    private final VirtualView virtual;
    private PrintWriter out;
    private final Connected connection;
    private final List<String> action= new ArrayList<>();
    private BufferedReader in;
    private String nickname;

    SocketConnection(Socket s,VirtualView virtual,Connected connection) {
        this.s = s;
        this.virtual = virtual;
        this.connection = connection;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream());
            String str = "";
            while(str!=null) {
                action.clear();
                str = in.readLine();
                if(str != null) {
                    StringTokenizer token = new StringTokenizer(str, "-");
                    while (token.hasMoreTokens())
                        action.add(token.nextToken());
                    sendMessage();
                }
            }
            this.logout();
        }catch(IOException e) {
            this.logout();
        }
    }

    private void sendMessage(){
        switch (action.get(0)) {
            case LOGIN:
                login(action.get(1));
                break;
            case DISCONNECTED:
                logout();
                break;
            case CHOOSE_SCHEMA:
                virtual.sendSchema(action.get(2), action.get(1));
                break;
            case INSERT_DICE:
                virtual.insertDice(nickname, Integer.parseInt(action.get(1)), Integer.parseInt(action.get(2)),
                        Integer.parseInt(action.get(3)));
                break;
            case USE_TOOL_CARD:
                virtual.useToolCard(nickname, Integer.parseInt(action.get(1)));
                break;
            case MOVE_DICE:
                virtual.moveDice(nickname, Integer.parseInt(action.get(1)), Integer.parseInt(action.get(2)),
                        Integer.parseInt(action.get(3)), Integer.parseInt(action.get(4)));
                break;
            case DRAFT_DICE:
                virtual.draftDice(nickname, Integer.parseInt(action.get(1)));
                break;
            case PLACE_DICE:
                virtual.placeDice(nickname, Integer.parseInt(action.get(1)), Integer.parseInt(action.get(2)));
                break;
            case CHANGE_VALUE:
                virtual.changeValue(nickname, action.get(1));
                break;
            case ROLL_DICE:
                virtual.rollDice(nickname);
                break;
            case SWAP_DICE:
                virtual.swapDice(nickname, Integer.parseInt(action.get(1)), Integer.parseInt(action.get(2)));
                break;
            case CANCEL_USE_TOOL_CARD:
                virtual.cancelUseToolCard(nickname);
                break;
            case END_TURN:
                virtual.sendEndTurn(nickname);
                break;
            case FLIP_DICE:
                virtual.flipDice(nickname);
                break;
            case PLACE_DICE_SPACE:
                virtual.placeDiceSpace(nickname);
                break;
            case ROLL_DICE_SPACE:
                virtual.rollDiceSpace(nickname);
                break;
            case SWAP_DICE_BAG:
                virtual.swapDiceBag(nickname);
                break;
            case CHOOSE_VALUE:
                virtual.chooseValue(nickname, Integer.parseInt(action.get(1)));
                break;
            case CUSTOM_SCHEMA:
                virtual.sendCustomSchema(action.get(2), action.get(1));
                break;
            default:
                break;
        }
    }

    private void login(String str) {
        Log.getLogger().addLog(str + CONNECTING_WITH_SOCKET, Level.INFO,this.getClass().getName(),SOCKET_CONNECTION_LOGIN);
        if(connection.checkUsername(str)) {
            nickname = str;
            connection.addPlayer(str,this);
            virtual.login(str);
        }else{
            loginError("username");
        }
    }

    private void logout() {
        try {
            in.close();
            out.close();
            s.close();
            if(connection.removePlayer(nickname)) {
                virtual.disconnected(nickname);
            }
        }catch(IOException io) {
            Log.getLogger().addLog(io.getMessage(),Level.SEVERE,this.getClass().getName(),SOCKET_CONNECTION_LOGOUT);
        }
    }


    public void login(String nickname, int lobbySize) {
        out.println(LOGIN_SUCCESSFUL + "-" +nickname + "-" + lobbySize);
        out.flush();
    }

    public void loginError(String cause) {
        out.println(LOGIN_ERROR + "-" + cause);
        out.flush();
    }

    public void playerDisconnected(String nickname){
        out.println(LOGOUT + "-" + nickname);
        out.flush();
    }

    public void timerPing(int timeLeft){
        out.println(TIMER_PING + "-" + timeLeft);
        out.flush();
    }

    public void turnTimerPing(int timeLeft) {
        out.println(TURN_TIMER_PING + "-" + timeLeft);
        out.flush();
    }

    public void createGame() {
        out.println(START_GAME);
        out.flush();
    }

    public void setSchemas(List<String> schemas) {
        StringBuilder message = new StringBuilder(SET_SCHEMAS);
        for(String schema: schemas)
            message.append("-").append(schema);
        out.println(message);
        out.flush();
    }

    public void setPrivateCard(String privateCard){
        out.println(SET_PRIVATE_CARD +"-" + privateCard);
        out.flush();
    }

    public void setPublicObjectives(List<String> publicObjectives){
        StringBuilder message = new StringBuilder(SET_PUBLIC_OBJECTIVES);
        for(String publicObjective: publicObjectives)
            message.append("-").append(publicObjective);
        out.println(message);
        out.flush();
    }

    public void setToolCards(List<Integer> toolCards) {
        StringBuilder message = new StringBuilder(SET_TOOL_CARDS);
        for(Integer toolCard: toolCards)
            message.append("-").append(toolCard);
        out.println(message);
        out.flush();
    }

    public void chooseSchema(String schema){
        out.println(APPROVED_SCHEMA +"-" + schema);
        out.flush();
    }

    public void setOpponentsSchemas(List<String> opponentsSchemas){
        StringBuilder message = new StringBuilder(SET_OPPONENTS_SCHEMAS);
        for(String string: opponentsSchemas)
            message.append("-").append(string);
        out.println(message);
        out.flush();
    }

    public void schemaCustomAccepted(String schema){
        out.println(APPROVED_SCHEMA_CUSTOM +"-" + schema);
        out.flush();
    }

    public void setOpponentsCustomSchemas(List<String> opponentsSchemas){
        StringBuilder message = new StringBuilder(SET_OPPONENTS_CUSTOM_SCHEMAS);
        for(String string: opponentsSchemas)
            message.append("-").append(string);
        out.println(message);
        out.flush();
    }

    public void startRound() {
        out.println(START_ROUND);
        out.flush();
    }

    public void startTurn(String nickname){
        out.println(START_TURN + "-" + nickname);
        out.flush();
    }

    public void setActions(List<String> actions){
        StringBuilder message = new StringBuilder(SET_ACTIONS);
        for(String string: actions)
            message.append("-").append(string);
        out.println(message);
        out.flush();
    }

    public void setDiceSpace(List<String> colours, List<Integer> values){
        StringBuilder message = new StringBuilder(SET_DICE_SPACE);
        for(int i= 0; i<colours.size(); i++){
            message.append("-").append(colours.get(i));
            message.append("-").append(values.get(i));
        }
        out.println(message);
        out.flush();
    }

    public void draftDiceAccepted(){
        out.println(DRAFT_DICE_ACCEPTED);
        out.flush();
    }

    public void insertDiceAccepted(){
        out.println(INSERT_DICE_ACCEPTED);
        out.flush();
    }

    public void moveDiceAccepted(){
        out.println(MOVE_DICE_ACCEPTED);
        out.flush();
    }

    public void moveDiceError(){
        out.println(MOVE_DICE_ERROR);
        out.flush();
    }

    public void pickDiceSpace(int index){
        out.println(PICK_DICE_SPACE + "-" + index);
        out.flush();
    }

    public void pickDiceSpaceError() {
        out.println(PICK_DICE_SPACE_ERROR);
        out.flush();
    }

    public void placeDiceSchema(String nickname, int row, int column, String colour, int value){
        out.println(PLACE_DICE_SCHEMA +"-"+nickname+"-"+row+"-"+column+"-"+colour+"-"+value);
        out.flush();
    }

    public void placeDiceSchemaError(){
        out.println(PLACE_DICE_SCHEMA_ERROR);
        out.flush();
    }

    public void pickDiceSchema(String nickname, int row, int column){
        out.println(PICK_DICE_SCHEMA +"-"+nickname+"-"+row+"-"+column);
        out.flush();
    }

    public void pickDiceSchemaError(){
        out.println(PICK_DICE_SCHEMA_ERROR);
        out.flush();
    }

    public void useToolCardAccepted(int favors) {
        out.println(USE_TOOL_CARD_ACCEPTED+"-"+favors);
        out.flush();
    }

    /**
     * notifies the client that the specified tool card has been used
     * @param toolCard is the tool card that has been used
     */
    public void usedToolCard(int toolCard) {
        out.println(USED_TOOL_CARD+"-"+toolCard);
        out.flush();
    }

    /**
     * notifies the client that the specified tool card has not been used
     * @param toolCard is the tool card that has not been used.
     */
    public void notUsedToolCard(int toolCard) {
        out.println(NOT_USED_TOOL_CARD+"-"+toolCard);
        out.flush();
    }

    public void useToolCardError() {
        out.println(USE_TOOL_CARD_ERROR);
        out.flush();
    }

    public void changeValueAccepted() {
        out.println(CHANGE_VALUE_ACCEPTED);
        out.flush();
    }

    public void changeValueError(){
        out.println(CHANGE_VALUE_ERROR);
        out.flush();
    }

    public void placeDiceAccepted(){
        out.println(PLACE_DICE_ACCEPTED);
        out.flush();
    }

    public void rollDiceAccepted(int value){
        out.println(ROLL_DICE_ACCEPTED+"-"+value);
        out.flush();
    }

    public void swapDiceAccepted(){
        out.println(SWAP_DICE_ACCEPTED);
        out.flush();
    }

    public void pickDiceRoundTrack(int nRound, int nDice){
        out.println(PICK_DICE_ROUND_TRACK+"-"+nRound+"-"+nDice);
        out.flush();
    }

    public void pickDiceRoundTrackError(){
        out.println(PICK_DICE_ROUND_TRACK_ERROR);
        out.flush();
    }

    public void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) {
        StringBuilder message = new StringBuilder(PLACE_DICE_ROUND_TRACK + "-" + nRound);
        for(int i= 0; i<colours.size(); i++){
            message.append("-").append(colours.get(i));
            message.append("-").append(values.get(i));
        }
        out.println(message);
        out.flush();
    }

    public void flipDiceAccepted(int value){
        out.println(FLIP_DICE_ACCEPTED +"-"+ value);
        out.flush();
    }

    public void cancelUseToolCardAccepted(int favor) {
        out.println(CANCEL_USE_TOOL_CARD_ACCEPTED +"-"+ favor);
        out.flush();
    }

    public void placeDiceSpace(String colour, int value){
        out.println(PLACE_DICE_DICE_SPACE +"-"+ colour + "-"+ value);
        out.flush();
    }

    public void placeDiceSpaceAccepted(){
        out.println(PLACE_DICE_SPACE_ACCEPTED);
        out.flush();
    }

    public void rollDiceSpaceAccepted(){
        out.println(ROLL_DICE_SPACE_ACCEPTED);
        out.flush();
    }

    public void swapDiceBagAccepted(String colour, int value){
        out.println(SWAP_DICE_BAG_ACCEPTED + "-" + colour + "-" + value);
        out.flush();
    }

    public void chooseValueAccepted(){
        out.println(CHOOSE_VALUE_ACCEPTED);
        out.flush();
    }

    public void chooseValueError() {
        out.println(CHOOSE_VALUE_ERROR);
        out.flush();
    }

    public void setWinner(String nickname) {
        out.println(SET_WINNER + "-" + nickname);
        out.flush();
    }

    public void setRankings(List<String> players, List<Integer> scores) {
        StringBuilder message = new StringBuilder(SET_RANKINGS);
        for(int i= 0; i<players.size(); i++){
            message.append("-").append(players.get(i));
            message.append("-").append(scores.get(i));
        }
        out.println(message);
        out.flush();
    }

    public void setSchemasOnReconnect(List<String> players, List<String> schemas) {
        StringBuilder message = new StringBuilder(SET_SCHEMAS_ON_RECONNECT);
        for(int i= 0; i<players.size(); i++){
            message.append("-").append(players.get(i));
            message.append("-").append(schemas.get(i));
        }
        out.println(message);
        out.flush();
    }
}

