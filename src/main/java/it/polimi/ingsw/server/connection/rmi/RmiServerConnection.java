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

    public void login(String nickname, int lobbySize) {
        try {
            client.login(nickname, lobbySize);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }
    }

    public void loginError(String cause) {
        try {
            client.loginError(cause);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }

    }

    public void playerDisconnected(String nickname){
        try {
            client.playerDisconnected(nickname);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }
    }

    public void timerPing(int timeLeft){
        try {
            client.timerPing(timeLeft);
        }catch (RemoteException e){
            serverMethod.disconnected(name);
        }
    }

    public void createGame() {
        try {
            client.createGame();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setSchemas(List<String> schemas) {
        try {
            client.setSchemas(schemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setPrivateCard(String privateCard){
        try {
            client.setPrivateCard(privateCard);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setPublicObjectives(List<String> publicObjectives){
        try {
            client.setPublicObjectives(publicObjectives);
        } catch (RemoteException e) {
             serverMethod.disconnected(name);
        }
    }

    public void setToolCards(List<Integer> toolCards) {
        try {
            client.setToolCards(toolCards);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void chooseSchema(String schema){
        try {
            client.chooseSchema(schema);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setOpponentsSchemas(List<String> opponentsSchemas){
        try {
            client.setOpponentsSchemas(opponentsSchemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void schemaCustomAccepted(String schema){
        try {
            client.schemaCustomAccepted(schema);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setOpponentsCustomSchemas(List<String> opponentsSchemas){
        try {
            client.setOpponentsCustomSchemas(opponentsSchemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void startRound() {
        try {
            client.startRound();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void startTurn(String nickname){
        try {
            client.startTurn(nickname);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setActions(List<String> actions){
        try {
            client.setActions(actions);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setDiceSpace(List<String> colours, List<Integer> values){
        try {
            client.setDiceSpace(colours,values);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void draftDiceAccepted(){
        try {
            client.draftDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void insertDiceAccepted(){
        try {
            client.insertDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void moveDiceAccepted(){
        try {
            client.moveDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void moveDiceError(){
        try {
            client.moveDiceError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void pickDiceSpace(int index){
        try {
            client.pickDiceSpace(index);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void pickDiceSpaceError() {
        try {
            client.pickDiceSpaceError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void placeDiceSchema(String nickname, int row, int column, String colour, int value){
        try {
            client.placeDiceSchema(nickname,row,column,colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void placeDiceSchemaError(){
        try {
            client.placeDiceSchemaError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void pickDiceSchema(String nickname, int row, int column){
        try {
            client.pickDiceSchema(nickname,row,column);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void pickDiceSchemaError(){
        try {
            client.pickDiceSchemaError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void useToolCardAccepted(int favors) {
        try {
            client.useToolCardAccepted(favors);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void useToolCardError() {
        try {
            client.useToolCardError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void changeValueAccepted() {
        try {
            client.changeValueAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void changeValueError(){
        try {
            client.changeValueError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void placeDiceAccepted(){
        try {
            client.placeDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void rollDiceAccepted(int value){
        try {
            client.rollDiceAccepted(value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void swapDiceAccepted(){
        try {
            client.swapDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void pickDiceRoundTrack(int nRound, int nDice){
        try {
            client.pickDiceRoundTrack(nRound,nDice);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void pickDiceRoundTrackError(){
        try {
            client.pickDiceRoundTrackError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) {
        try {
            client.placeDiceRoundTrack(nRound,colours,values);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void flipDiceAccepted(int value){
        try {
            client.flipDiceAccepted(value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void cancelUseToolCardAccepted(int favor) {
        try {
            client.cancelUseToolCardAccepted(favor);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void placeDiceSpace(String colour, int value){
        try {
            client.placeDiceSpace(colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void placeDiceSpaceAccepted(){
        try {
            client.placeDiceSpaceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void rollDiceSpaceAccepted(){
        try {
            client.rollDiceSpaceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void swapDiceBagAccepted(String colour, int value){
        try {
            client.swapDiceBagAccepted(colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void chooseValueAccepted(){
        try {
            client.chooseValueAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void chooseValueError() {
        try {
            client.chooseValueError();
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setWinner(String nickname) {
        try {
            client.setWinner(nickname);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

    public void setRankings(List<String> players, List<Integer> scores) {
        try {
            client.setRankings(players,scores);
        } catch (RemoteException e) {
            serverMethod.disconnected(name);
        }
    }

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
}
