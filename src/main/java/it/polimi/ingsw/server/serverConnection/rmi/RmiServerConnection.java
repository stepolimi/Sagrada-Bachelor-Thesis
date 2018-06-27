package it.polimi.ingsw.server.serverConnection.rmi;

import it.polimi.ingsw.client.clientConnection.rmi.RmiClientMethodInterface;
import it.polimi.ingsw.server.serverConnection.Connection;

import java.rmi.RemoteException;
import java.util.List;

public class RmiServerConnection implements Connection {
    private RmiClientMethodInterface client;
    private RmiServerMethod serverMethod;

    RmiServerConnection(RmiClientMethodInterface client, RmiServerMethod serverMethod) {
        this.client = client;
        this.serverMethod = serverMethod;
    }

    public void login(String nickname, int lobbySize) {
        try {
            client.login(nickname, lobbySize);
        }catch (RemoteException e){
            serverMethod.disconnected(this.client);
        }
    }

    public void loginError(String cause) {
        try {
            client.loginError(cause);
        }catch (RemoteException e){
            serverMethod.disconnected(this.client);
        }

    }

    public void playerDisconnected(String nickname){
        try {
            client.playerDisconnected(nickname);
        }catch (RemoteException e){
            serverMethod.disconnected(this.client);
        }
    }

    public void timerPing(int timeLeft){
        try {
            client.timerPing(timeLeft);
        }catch (RemoteException e){
            serverMethod.disconnected(this.client);
        }
    }

    public void createGame() {
        try {
            client.createGame();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setSchemas(List<String> schemas) {
        try {
            client.setSchemas(schemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setPrivateCard(String privateCard){
        try {
            client.setPrivateCard(privateCard);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setPublicObjectives(List<String> publicObjectives){
        try {
            client.setPublicObjectives(publicObjectives);
        } catch (RemoteException e) {
             serverMethod.disconnected(this.client);
        }
    }

    public void setToolCards(List<Integer> toolCards) {
        try {
            client.setToolCards(toolCards);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void chooseSchema(String schema){
        try {
            client.chooseSchema(schema);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setOpponentsSchemas(List<String> opponentsSchemas){
        try {
            client.setOpponentsSchemas(opponentsSchemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void schemaCustomAccepted(String schema){
        try {
            client.schemaCustomAccepted(schema);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setOpponentsCustomSchemas(List<String> opponentsSchemas){
        try {
            client.setOpponentsCustomSchemas(opponentsSchemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void startRound() {
        try {
            client.startRound();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void startTurn(String nickname){
        try {
            client.startTurn(nickname);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setActions(List<String> actions){
        try {
            client.setActions(actions);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setDiceSpace(List<String> colours, List<Integer> values){
        try {
            client.setDiceSpace(colours,values);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void draftDiceAccepted(){
        try {
            client.draftDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void insertDiceAccepted(){
        try {
            client.insertDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void moveDiceAccepted(){
        try {
            client.moveDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void pickDiceSpace(int index){
        try {
            client.pickDiceSpace(index);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void pickDiceSpaceError() {
        try {
            client.pickDiceSpaceError();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void placeDiceSchema(String nickname, int row, int column, String colour, int value){
        try {
            client.placeDiceSchema(nickname,row,column,colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void placeDiceSchemaError(){
        try {
            client.placeDiceSchemaError();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void pickDiceSchema(String nickname, int row, int column){
        try {
            client.pickDiceSchema(nickname,row,column);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void pickDiceSchemaError(){
        try {
            client.pickDiceSchemaError();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void useToolCardAccepted(int favors) {
        try {
            client.useToolCardAccepted(favors);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void useToolCardError() {
        try {
            client.useToolCardError();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void changeValueAccepted() {
        try {
            client.changeValueAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void changeValueError(){
        try {
            client.changeValueError();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void placeDiceAccepted(){
        try {
            client.placeDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void rollDiceAccepted(int value){
        try {
            client.rollDiceAccepted(value);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void swapDiceAccepted(){
        try {
            client.swapDiceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void pickDiceRoundTrack(int nRound, int nDice){
        try {
            client.pickDiceRoundTrack(nRound,nDice);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void pickDiceRoundTrackError(){
        try {
            client.pickDiceRoundTrackError();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) {
        try {
            client.placeDiceRoundTrack(nRound,colours,values);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void flipDiceAccepted(int value){
        try {
            client.flipDiceAccepted(value);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void cancelUseToolCardAccepted(int favor) {
        try {
            client.cancelUseToolCardAccepted(favor);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void placeDiceSpace(String colour, int value){
        try {
            client.placeDiceSpace(colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void placeDiceSpaceAccepted(){
        try {
            client.placeDiceSpaceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void rollDiceSpaceAccepted(){
        try {
            client.rollDiceSpaceAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void swapDiceBagAccepted(String colour, int value){
        try {
            client.swapDiceBagAccepted(colour,value);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void chooseValueAccepted(){
        try {
            client.chooseValueAccepted();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void chooseValueError() {
        try {
            client.chooseValueError();
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setWinner(String nickname) {
        try {
            client.setWinner(nickname);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setRankings(List<String> players, List<Integer> scores) {
        try {
            client.setRankings(players,scores);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    public void setSchemasOnReconnect(List<String> players, List<String> schemas) {
        try {
            client.setSchemasOnReconnect(players,schemas);
        } catch (RemoteException e) {
            serverMethod.disconnected(this.client);
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
