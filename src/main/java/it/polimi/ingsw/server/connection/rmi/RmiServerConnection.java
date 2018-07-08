package it.polimi.ingsw.server.connection.rmi;

import it.polimi.ingsw.client.clientConnection.rmi.RmiClientMethodInterface;
import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.connection.Connection;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.PING_ERROR;
import static it.polimi.ingsw.server.costants.LogConstants.PLAYER_DISCONNECTED;
import static it.polimi.ingsw.server.costants.LogConstants.RMI_SERVER_CONNECTION_PING_CLIENT;
import static java.lang.Thread.sleep;

public class RmiServerConnection implements Connection {
    private final RmiClientMethodInterface client;
    private final RmiServerMethod serverMethod;
    private final String name;
    RmiServerConnection(RmiClientMethodInterface client, RmiServerMethod serverMethod,String name) {
        this.client = client;
        this.serverMethod = serverMethod;
        this.name = name;
        pingClient();
    }
    /**
     * check connection with client
     */
    private void pingClient() {
        Thread t = new Thread(() -> {
            boolean  isRunning = true;
            while(isRunning) {
                try {
                    client.ping();
                    sleep(5000);
                } catch (RemoteException e) {
                    Log.getLogger().addLog(PLAYER_DISCONNECTED,Level.INFO,this.getClass().getName(),RMI_SERVER_CONNECTION_PING_CLIENT);
                    serverMethod.disconnected(name);
                    isRunning = false;
                    } catch (InterruptedException ex){
                    Log.getLogger().addLog(PING_ERROR, Level.SEVERE,this.getClass().getName(),RMI_SERVER_CONNECTION_PING_CLIENT);
                }
            }
        });
        t.start();
    }
    /**
     * invoked to connect player
     * @param nickname is player's name
     * @param lobbySize is number of player in lobby
     */
    public void login(String nickname, int lobbySize) {
        try {
            client.login(nickname, lobbySize);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }
    }

    /**
     * invoked when login failed
     * @param cause is cause of the error
     */
    public void loginError(String cause) {
        try {
            client.loginError(cause);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }

    }

    /**
     * invoked by player when want to disconnect
     * @param nickname is player's name disconnected
     */
    public void playerDisconnected(String nickname){
        try {
            client.playerDisconnected(nickname);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }
    }

    /**
     * lobby timer
     * @param timeLeft is the remaining lobby time
     */
    public void timerPing(int timeLeft){
        try {
            client.timerPing(timeLeft);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }
    }

    /** turn timer
     * @param timeLeft is the remaining turn time
     */
    public void turnTimerPing(int timeLeft) {
        try {
            client.turnTimerPing(timeLeft);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }
    }

    /**
     * Invoked when start the game
     */
    public void createGame() {
        try {
            client.createGame();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * set player's schema
     * @param schemas are the list of players' schemes
     */
    public void setSchemas(List<String> schemas) {
        try {
            client.setSchemas(schemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * set private objective
     * @param privateCard is  player's private objective
     */
    public void setPrivateCard(String privateCard){
        try {
            client.setPrivateCard(privateCard);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * set public objective
     * @param publicObjectives are public objective of game
     */
    public void setPublicObjectives(List<String> publicObjectives){
        try {
            client.setPublicObjectives(publicObjectives);
        } catch (RemoteException e) {
             serverMethod.disconnected(name);
        }
    }

    /**
     * set tool card
     * @param toolCards are tool card of game
     */
    public void setToolCards(List<Integer> toolCards) {
        try {
            client.setToolCards(toolCards);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * set own scheme
     * @param schema is the own scheme
     */
    public void chooseSchema(String schema){
        try {
            client.chooseSchema(schema);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * set opponents schemas
     * @param opponentsSchemas are the opponents schemas
     */
    public void setOpponentsSchemas(List<String> opponentsSchemas){
        try {
            client.setOpponentsSchemas(opponentsSchemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * confirm custom schema
     * @param schema is name of custom schema
     */
    public void schemaCustomAccepted(String schema){
        try {
            client.schemaCustomAccepted(schema);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * set opponents custom schemas
     * @param opponentsSchemas are custom schemas
     */
    public void setOpponentsCustomSchemas(List<String> opponentsSchemas){
        try {
            client.setOpponentsCustomSchemas(opponentsSchemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     *  invoked when start round
     */
    public void startRound() {
        try {
            client.startRound();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * invoked when the turn starts
     * @param nickname is the name of the player to whom the turn is assigned
     */
    public void startTurn(String nickname){
        try {
            client.startTurn(nickname);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * used to set legal action
     * @param actions are the possible actions
     */
    public void setActions(List<String> actions){
        try {
            client.setActions(actions);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * used to set round's diceSpace
     * @param colours are the dice colors of the DiceSpaceused to set round's diceSpace
     *@param values are the dice value of the DiceSpace
     */
    public void setDiceSpace(List<String> colours, List<Integer> values){
        try {
            client.setDiceSpace(colours,values);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * confirm draft dice
     */
    public void draftDiceAccepted(){
        try {
            client.draftDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }


    /**
     * invoked by server to accept InsertDice action
     */
    public void insertDiceAccepted(){
        try {
            client.insertDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * confirm move dice
     */
    public void moveDiceAccepted(){
        try {
            client.moveDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * reject move dice
     */
    public void moveDiceError(){
        try {
            client.moveDiceError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * used to remove Dice from DiceSpace
     * @param index of Dice in DiceSpace
     */
    public void pickDiceSpace(int index){
        try {
            client.pickDiceSpace(index);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     *  used to notify the user of an insertDiceSpace error
     */
    public void pickDiceSpaceError() {
        try {
            client.pickDiceSpaceError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * used to place a Dice in Schema
     * @param nickname is the name of the player to insert the die in the scheme
     * @param row is index of row of scheme
     * @param column is inde of column of schema
     * @param colour is colour of die
     * @param value is value of die
     */
    public void placeDiceSchema(String nickname, int row, int column, String colour, int value){
        try {
            client.placeDiceSchema(nickname,row,column,colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * used to notify the user of an placeDiceSchema error
     */
    public void placeDiceSchemaError(){
        try {
            client.placeDiceSchemaError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * pick dice from schema
     * @param nickname is the owner of scheme
     * @param row is index of row
     * @param column is index of column
     */
    public void pickDiceSchema(String nickname, int row, int column){
        try {
            client.pickDiceSchema(nickname,row,column);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * reject pick dice schema
     */
    public void pickDiceSchemaError(){
        try {
            client.pickDiceSchemaError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * confirms that the use of the toolcard has been accepted
     * @param favors is favor remain
     */
    public void useToolCardAccepted(int favors) {
        try {
            client.useToolCardAccepted(favors);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * notifies the client that the specified tool card has been used
     * @param toolCard is the tool card that has been used
     */
    public void usedToolCard(int toolCard) {
        try {
            client.usedToolCard(toolCard);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * notifies the client that the specified tool card has not been used
     * @param toolCard is the tool card that has not been used.
     */
    public void notUsedToolCard(int toolCard) {
        try {
            client.notUsedToolCard(toolCard);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * rejects the use of the toolcard
     */
    public void useToolCardError() {
        try {
            client.useToolCardError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * confirms that change value has been accepted
     */
    public void changeValueAccepted() {
        try {
            client.changeValueAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * rejects change value
     */
    public void changeValueError(){
        try {
            client.changeValueError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * confirms place dice
     */
    public void placeDiceAccepted(){
        try {
            client.placeDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }


    /**
     * confirm roll dice
     * @param value is the new value of dice
     */
    public void rollDiceAccepted(int value){
        try {
            client.rollDiceAccepted(value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * confirm exchange dice
     */
    public void swapDiceAccepted(){
        try {
            client.swapDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * pick dice from  round track
     * @param nRound is the index of round track
     * @param nDice  is the index of dice
     */
    public void pickDiceRoundTrack(int nRound, int nDice){
        try {
            client.pickDiceRoundTrack(nRound,nDice);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * reject pick dice from round track
     */
    public void pickDiceRoundTrackError(){
        try {
            client.pickDiceRoundTrackError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * place dices to round track
     * @param nRound is index of round track
     * @param colours are colours of dices
     * @param values are value of dices
     */
    public void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) {
        try {
            client.placeDiceRoundTrack(nRound,colours,values);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * confirm flip dice
     * @param value is new value of the dice
     */
    public void flipDiceAccepted(int value){
        try {
            client.flipDiceAccepted(value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * confirm the invocation of the method cancelUseToolCard
     * @param favor is favors remain
     */
    public void cancelUseToolCardAccepted(int favor) {
        try {
            client.cancelUseToolCardAccepted(favor);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * place dice in dice space
     * @param colour is the colour of dice
     * @param value is the value of dice
     */
    public void placeDiceSpace(String colour, int value){
        try {
            client.placeDiceSpace(colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * confirm place dice in dice space
     */
    public void placeDiceSpaceAccepted(){
        try {
            client.placeDiceSpaceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * confirm roll dice space
     */
    public void rollDiceSpaceAccepted(){
        try {
            client.rollDiceSpaceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * confirm swap dice bag
     * @param colour is the new colour of dice
     * @param value is the new value of dice
     */
    public void swapDiceBagAccepted(String colour, int value){
        try {
            client.swapDiceBagAccepted(colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * confirm value accepted
     */
    public void chooseValueAccepted(){
        try {
            client.chooseValueAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * reject choose value
     */
    public void chooseValueError() {
        try {
            client.chooseValueError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * set winner player
     * @param nickname is name of the winner
     */
    public void setWinner(String nickname) {
        try {
            client.setWinner(nickname);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }
    /**
     * set ranking
     * @param players are the names of players
     * @param scores are the scores of players
     */
    public void setRankings(List<String> players, List<Integer> scores) {
        try {
            client.setRankings(players,scores);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    /**
     * @param players are the name of players
     * @param schemas are the schemas of players
     */
    public void setSchemasOnReconnect(List<String> players, List<String> schemas) {
        try {
            client.setSchemasOnReconnect(players,schemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj.getClass().equals(RmiServerConnection.class)))
            return false;

        RmiServerConnection cli = (RmiServerConnection) obj;
        return (this.client.equals(cli.client));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
