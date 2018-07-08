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

    /**
     * socket is listenning server's message
     */
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

    /**
     * in function of action arrived, this call different method
     */
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

    /**
     * it accepts login of player
     * @param str nickname of player
     */
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

    /**
     * it accepts logout of player
     */
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


    /**
     * It notifies login successfull
     * @param nickname player connected
     * @param lobbySize value of lobby
     */
    public void login(String nickname, int lobbySize) {
        out.println(LOGIN_SUCCESSFUL + "-" +nickname + "-" + lobbySize);
        out.flush();
    }

    /**
     * It notifies login successfull
     * @param cause cause of login error
     */
    public void loginError(String cause) {
        out.println(LOGIN_ERROR + "-" + cause);
        out.flush();
    }

    /**
     * It notifies player disconected
     * @param nickname plyer disconnected
     */
    public void playerDisconnected(String nickname){
        out.println(LOGOUT + "-" + nickname);
        out.flush();
    }

    /**
     * It notifies time elapsed in lobby
     * @param timeLeft time elapsed
     */
    public void timerPing(int timeLeft){
        out.println(TIMER_PING + "-" + timeLeft);
        out.flush();
    }

    /**
     * It notifies time elapsed in player's turn
     * @param timeLeft time elapsed
     */
    public void turnTimerPing(int timeLeft) {
        out.println(TURN_TIMER_PING + "-" + timeLeft);
        out.flush();
    }

    /**
     * It notifies creation of game
     */
    public void createGame() {
        out.println(START_GAME);
        out.flush();
    }

    /**
     * it send schema to choose
     * @param schemas List of Schemas
     */
    public void setSchemas(List<String> schemas) {
        StringBuilder message = new StringBuilder(SET_SCHEMAS);
        for(String schema: schemas)
            message.append("-").append(schema);
        out.println(message);
        out.flush();
    }

    /**
     * it send private card
     * @param privateCard name of private card
     */
    public void setPrivateCard(String privateCard){
        out.println(SET_PRIVATE_CARD +"-" + privateCard);
        out.flush();
    }

    /**
     * it send private card
     * @param publicObjectives name of public objective
     */
    public void setPublicObjectives(List<String> publicObjectives){
        StringBuilder message = new StringBuilder(SET_PUBLIC_OBJECTIVES);
        for(String publicObjective: publicObjectives)
            message.append("-").append(publicObjective);
        out.println(message);
        out.flush();
    }

    /**
     * it send tool card
     * @param toolCards name of tool card
     */
    public void setToolCards(List<Integer> toolCards) {
        StringBuilder message = new StringBuilder(SET_TOOL_CARDS);
        for(Integer toolCard: toolCards)
            message.append("-").append(toolCard);
        out.println(message);
        out.flush();
    }

    /**
     * it sends schema approved
     * @param schema schema choosen
     */
    public void chooseSchema(String schema){
        out.println(APPROVED_SCHEMA +"-" + schema);
        out.flush();
    }

    /**
     * It sent schema of opponents player
     * @param opponentsSchemas List of Schema
     */
    public void setOpponentsSchemas(List<String> opponentsSchemas){
        StringBuilder message = new StringBuilder(SET_OPPONENTS_SCHEMAS);
        for(String string: opponentsSchemas)
            message.append("-").append(string);
        out.println(message);
        out.flush();
    }

    /**
     * it send schema accepted to player who load a schema from file
     * @param schema schema loaded
     */
    public void schemaCustomAccepted(String schema){
        out.println(APPROVED_SCHEMA_CUSTOM +"-" + schema);
        out.flush();
    }

    /**
     * It sent schema of opponents player who load schema from file
     * @param opponentsSchemas List of Schema
     * */
    public void setOpponentsCustomSchemas(List<String> opponentsSchemas){
        StringBuilder message = new StringBuilder(SET_OPPONENTS_CUSTOM_SCHEMAS);
        for(String string: opponentsSchemas)
            message.append("-").append(string);
        out.println(message);
        out.flush();
    }

    /**
     * it notifies that round starts
     */
    public void startRound() {
        out.println(START_ROUND);
        out.flush();
    }

    /**
     * it notifies that round starts and it's the @player's turn
     * @param nickname name of player
     */
    public void startTurn(String nickname){
        out.println(START_TURN + "-" + nickname);
        out.flush();
    }

    /**
     * It sends possibile action who every player could do
     * @param actions List of action player could do
     */
    public void setActions(List<String> actions){
        StringBuilder message = new StringBuilder(SET_ACTIONS);
        for(String string: actions)
            message.append("-").append(string);
        out.println(message);
        out.flush();
    }

    /**
     * It sends to every player Dice in DiceSpace
     * @param colours List of color
     * @param values List of value of dice
     */
    public void setDiceSpace(List<String> colours, List<Integer> values){
        StringBuilder message = new StringBuilder(SET_DICE_SPACE);
        for(int i= 0; i<colours.size(); i++){
            message.append("-").append(colours.get(i));
            message.append("-").append(values.get(i));
        }
        out.println(message);
        out.flush();
    }

    /**
     * It notifies the correct DraftDice
     */
    public void draftDiceAccepted(){
        out.println(DRAFT_DICE_ACCEPTED);
        out.flush();
    }

    /**
     * It notifies the correct InsertDice
     */
    public void insertDiceAccepted(){
        out.println(INSERT_DICE_ACCEPTED);
        out.flush();
    }

    /**
     * It notifies the correct MoveDice
     */
    public void moveDiceAccepted(){
        out.println(MOVE_DICE_ACCEPTED);
        out.flush();
    }

    /**
     * It notifies the wrong MoveDice
     */
    public void moveDiceError(){
        out.println(MOVE_DICE_ERROR);
        out.flush();
    }

    /**
     * it notifies that a player picks a dice from DiceSpace
     * @param index index of DiceSpace
     */
    public void pickDiceSpace(int index){
        out.println(PICK_DICE_SPACE + "-" + index);
        out.flush();
    }

    /**
     * it notifies if player picks wrongly a dice from DiceSpace
     */
    public void pickDiceSpaceError() {
        out.println(PICK_DICE_SPACE_ERROR);
        out.flush();
    }

    /**
     * It notifies correct place of a dice in schema
     * @param nickname name of player
     * @param row index row
     * @param column index column
     * @param colour color of dice
     * @param value value of dice
     */
    public void placeDiceSchema(String nickname, int row, int column, String colour, int value){
        out.println(PLACE_DICE_SCHEMA +"-"+nickname+"-"+row+"-"+column+"-"+colour+"-"+value);
        out.flush();
    }

    /**
     * It notifies wrong place of a dice in schema
     */
    public void placeDiceSchemaError(){
        out.println(PLACE_DICE_SCHEMA_ERROR);
        out.flush();
    }

    /**
     * @nickname pick correct dice from his schema in cell(row, column)
     * @param nickname name of player
     * @param row row index
     * @param column columns index
     */
    public void pickDiceSchema(String nickname, int row, int column){
        out.println(PICK_DICE_SCHEMA +"-"+nickname+"-"+row+"-"+column);
        out.flush();
    }

    /**
     * player picks wrong dice from his schema
     */
    public void pickDiceSchemaError(){
        out.println(PICK_DICE_SCHEMA_ERROR);
        out.flush();
    }

    /**
     * it notifies that player can use toolcard
     * @param favors number of favour
     */
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

    /**
     * it notifies that player can't use toolcard
     */
    public void useToolCardError() {
        out.println(USE_TOOL_CARD_ERROR);
        out.flush();
    }

    /**
     * It notifies the correct ChangeValue
     */
    public void changeValueAccepted() {
        out.println(CHANGE_VALUE_ACCEPTED);
        out.flush();
    }

    /**
     * It notifies the wrong ChangeValue
     */
    public void changeValueError(){
        out.println(CHANGE_VALUE_ERROR);
        out.flush();
    }

    /**
     * It notifies the correct PlaceDice action
     */
    public void placeDiceAccepted(){
        out.println(PLACE_DICE_ACCEPTED);
        out.flush();
    }

    /**
     * It notifies the correct roolDice action
     * @param value value of dice
     */
    public void rollDiceAccepted(int value){
        out.println(ROLL_DICE_ACCEPTED+"-"+value);
        out.flush();
    }

    /**
     * It notifies the correct SwapDice action
     */
    public void swapDiceAccepted(){
        out.println(SWAP_DICE_ACCEPTED);
        out.flush();
    }

    /**
     * It notifies the correct pick dice from a round of Roundtrack
     * @param nRound number of round
     * @param nDice index of dice
     */
    public void pickDiceRoundTrack(int nRound, int nDice){
        out.println(PICK_DICE_ROUND_TRACK+"-"+nRound+"-"+nDice);
        out.flush();
    }

    /**
     * It notifies the wrong pick dice from a round of Roundtrack
     */
    public void pickDiceRoundTrackError(){
        out.println(PICK_DICE_ROUND_TRACK_ERROR);
        out.flush();
    }

    /**
     * it notifies the place of dice in roundTrack
     * @param nRound number of round
     * @param colours list of color
     * @param values list of value
     */
    public void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) {
        StringBuilder message = new StringBuilder(PLACE_DICE_ROUND_TRACK + "-" + nRound);
        for(int i= 0; i<colours.size(); i++){
            message.append("-").append(colours.get(i));
            message.append("-").append(values.get(i));
        }
        out.println(message);
        out.flush();
    }

    /**
     * it notifies che correct flipDice action
     * @param value value of dice
     */
    public void flipDiceAccepted(int value){
        out.println(FLIP_DICE_ACCEPTED +"-"+ value);
        out.flush();
    }

    /**
     * it notifies che correct cancel use toolcard action
     * @param favor new favour after cancelling toolcard
     */
    public void cancelUseToolCardAccepted(int favor) {
        out.println(CANCEL_USE_TOOL_CARD_ACCEPTED +"-"+ favor);
        out.flush();
    }

    /**
     * it notifies che correct cancel use toolcard action
     * @param colour color
     * @param value value
     */
    public void placeDiceSpace(String colour, int value){
        out.println(PLACE_DICE_DICE_SPACE +"-"+ colour + "-"+ value);
        out.flush();
    }

    /**
     * it notifies che correct PlaceDiceSpace
     */
    public void placeDiceSpaceAccepted(){
        out.println(PLACE_DICE_SPACE_ACCEPTED);
        out.flush();
    }

    /**
     * it notifies che correct RollDiceSpace action
     */
    public void rollDiceSpaceAccepted(){
        out.println(ROLL_DICE_SPACE_ACCEPTED);
        out.flush();
    }

    /**
     * it notifies che correct SwapDiceBag action
     * @param colour color of dice
     * @param value value of dice
     */
    public void swapDiceBagAccepted(String colour, int value){
        out.println(SWAP_DICE_BAG_ACCEPTED + "-" + colour + "-" + value);
        out.flush();
    }

    /**
     * it notifies the correct ChooseValue action
     */
    public void chooseValueAccepted(){
        out.println(CHOOSE_VALUE_ACCEPTED);
        out.flush();
    }

    /**
     * it notifies the wrong ChooseValue action
     */
    public void chooseValueError() {
        out.println(CHOOSE_VALUE_ERROR);
        out.flush();
    }

    /**it send the plyer who win the game
     * @param nickname player who wins
     */
    public void setWinner(String nickname) {
        out.println(SET_WINNER + "-" + nickname);
        out.flush();
    }

    /**
     * it sent to player thei score sorted
     * @param players List of player sorted
     * @param scores respective scre of every single player
     */
    public void setRankings(List<String> players, List<Integer> scores) {
        StringBuilder message = new StringBuilder(SET_RANKINGS);
        for(int i= 0; i<players.size(); i++){
            message.append("-").append(players.get(i));
            message.append("-").append(scores.get(i));
        }
        out.println(message);
        out.flush();
    }

    /**
     * it sent schemas and nickname's player on reconnection
     * @param players List of player
     * @param schemas List of Schema
     */
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

