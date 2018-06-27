package it.polimi.ingsw.client.clientConnection.rmi;


import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.client.view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static it.polimi.ingsw.client.constants.MessageConstants.LOGIN_ERROR;
import static it.polimi.ingsw.client.constants.MessageConstants.LOGIN_SUCCESSFUL;


public class RmiClientMethod extends UnicastRemoteObject implements RmiClientMethodInterface {
    private Handler hand;
    private View v;

    RmiClientMethod(Handler hand) throws RemoteException {
        this.hand = hand;
        this.v = hand.getView();
    }

    public void login(String nickname, int lobbySize) {
        if (nickname.equals(v.getName())) {
            v.login(LOGIN_SUCCESSFUL);
            v.setNumberPlayer(lobbySize);
        }else
            v.playerConnected(nickname);
    }

    public void loginError(String cause) {
        v.login(LOGIN_ERROR + "-" + cause);
    }

    public void playerDisconnected(String nickname) {
        v.playerDisconnected(nickname);
    }

    public void timerPing(int timeLeft) {
        v.timerPing(((Integer)timeLeft).toString());
    }

    public void createGame() {
        v.createGame();
    }

    public void setSchemas(List<String> schemas) { v.setSchemas(schemas); }

    public void setPrivateCard(String privateCard) {
        v.setPrivateCard(privateCard);
    }

    public void setPublicObjectives(List<String> publicObjectives) { v.setPublicObjectives(publicObjectives); }

    public void setToolCards(List<Integer> toolCards) { v.setToolCards(toolCards); }

    public void chooseSchema(String schema) { v.chooseSchema(schema); }

    public void setOpponentsSchemas(List<String> opponentsSchemas) {
        v.setOpponentsSchemas(opponentsSchemas);
    }

    public void startRound() {
        v.startRound();
    }

    public void startTurn(String nickname) {
        v.startTurn(nickname);
    }

    public void setActions(List<String> actions) { v.setActions(actions); }

    public void setDiceSpace(List<String> colours, List<Integer> values) { v.setDiceSpace(colours,values); }

    public void insertDiceAccepted() {
        v.insertDiceAccepted();
    }

    public void draftDiceAccepted() {
        v.draftDiceAccepted();
    }

    public void moveDiceAccepted() {
        v.moveDiceAccepted();
    }

    public void pickDiceSpace(int index) {
        try {
            v.pickDiceSpace(index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pickDiceSpaceError() {
        v.pickDiceSpaceError();
    }

    public void placeDiceSchema(String nickname, int row, int column, String colour, int value) { v.placeDiceSchema(nickname,row,column,colour,value); }

    public void placeDiceSchemaError() {
        v.placeDiceSchemaError();
    }

    public void pickDiceSchema(String nickname, int row, int column) { v.pickDiceSchema(nickname,row,column); }

    public void pickDiceSchemaError() {
        v.pickDiceSchemaError();
    }

    public void useToolCardAccepted(int favors) {
        v.useToolCardAccepted(favors);
    }

    public void useToolCardError() {
        v.useToolCardError();
    }

    public void changeValueAccepted() {
        v.changeValueAccepted();
    }

    public void changeValueError() {
        v.changeValueError();
    }

    public void placeDiceAccepted() {
        v.placeDiceAccepted();
    }

    public void rollDiceAccepted(int value) {
        v.rollDiceAccepted(value);
    }

    public void swapDiceAccepted() {
        v.swapDiceAccepted();
    }

    public void pickDiceRoundTrack(int nRound, int nDice) { v.pickDiceRoundTrack(nRound,nDice); }

    public void pickDiceRoundTrackError() {
        v.pickDiceRoundTrackError();
    }

    public void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) { v.placeDiceRoundTrack(nRound, colours, values); }

    public void flipDiceAccepted(int value) {
        v.flipDiceAccepted(value);
    }

    public void cancelUseToolCardAccepted(int favors) {
        v.cancelUseToolCardAccepted(favors);
    }

    public void placeDiceSpace(String colour, int value) { v.placeDiceSpace(colour,value); }

    public void placeDiceSpaceAccepted() {
        v.placeDiceSpaceAccepted();
    }

    public void rollDiceSpaceAccepted() {
        v.rollDiceSpaceAccepted();
    }

    public void swapDiceBagAccepted(String colour, int value) { v.swapDiceBagAccepted(colour,value); }

    public void chooseValueAccepted() {
        v.chooseValueAccepted();
    }

    public void chooseValueError() {
        v.chooseValueError();
    }

    public void setWinner(String nickname) { v.setWinner(nickname); }

    public void setRankings(List<String> players, List<Integer> scores) {
        v.setRankings(players,scores);
    }

    public void setSchemasOnReconnect(List<String> players, List<String> schemas) {
        v.setSchemasOnReconnect(players,schemas);
    }

    public void schemaCustomAccepted(String schema){
        v.schemaCustomAccepted(schema);
    }

    public void setOpponentsCustomSchemas(List<String> opponentsSchemas) { v.setOpponentsCustomSchemas(opponentsSchemas); }
}


