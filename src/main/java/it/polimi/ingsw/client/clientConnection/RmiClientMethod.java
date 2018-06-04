package it.polimi.ingsw.client.clientConnection;


import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.client.view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static it.polimi.ingsw.costants.LoginMessages.LOGIN_SUCCESSFUL;


public class RmiClientMethod extends UnicastRemoteObject implements RmiClientMethodInterface {
    Handler hand;
    View v;
    public RmiClientMethod(Handler hand) throws RemoteException {
        this.hand = hand;
        this.v = hand.getView();
    }

    public void updateText(String s) throws RemoteException {}

    public void printText(String str) {
        /*List action = new ArrayList();
        hand.deliverGI(action);
        System.out.println(str);*/
    }

    public void login(List action)throws RemoteException{
        if(action.get(0).equals(LOGIN_SUCCESSFUL)) {
            if (action.get(1).equals(v.getName()))
                v.login((String)action.get(0));
            else
                v.playerConnected((String)action.get(1));
        }else
            v.login(action.get(0) + "-" + action.get(1));
    }

    public void playerDisconnected(List action) {
        v.playerDisconnected((String)action.get(1));
    }

    public void timerPing(List action)throws RemoteException{
        v.timerPing((String)action.get(1));
    }

    public void createGame()throws RemoteException{
        v.createGame();
    }

    public void setSchemas(List action)throws RemoteException{
        v.setSchemas(action.subList(1,action.size()));

    }

    public void setPrivateCard(String privateCard)throws RemoteException{
        v.setPrivateCard(privateCard);
    }

    public void setPublicObjectives(List action)throws RemoteException{
        v.setPublicObjectives(action.subList(1,action.size()));
    }

    public void setToolCards(List action)throws RemoteException{
        v.setToolCards(action.subList(1,action.size()));
    }
    public void setOpponentsSchemas()
    {
       // v.setOpponentsSchemas();
    }

    public void chooseSchema(List action)
    {
        v.chooseSchema((String)action.get(1));
    }

    public void setOpponentsSchemas(List action) throws RemoteException {
        v.setOpponentsSchemas(action.subList(1,action.size()));
    }

    public void startRound() {
        v.startRound();
    }

    public void startTurn(List action) {
        v.startTurn((String)action.get(1));
    }

    public void setActions(List action) {
        v.setActions(action.subList(1,action.size()));
    }

    public void setDiceSpace(List action) {
        v.setDiceSpace(action.subList(1,action.size()));
    }

    public void insertDiceAccepted() {
        v.insertDiceAccepted();
    }

    public void draftDiceAccepted() {
        v.pickDiceAccepted();
    }

    public void moveDiceAccepted() {
        v.moveDiceAccepted();
    }

    public void pickDiceSpace(List action) {
        try {
            v.pickDiceSpace(action.subList(1,action.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pickDiceSpaceError() {
        v.pickDiceSpaceError();
    }

    public void placeDiceSchema(List action) {
        v.placeDiceSchema(action.subList(1,action.size()));
    }

    public void placeDiceSchemaError() {
        v.placeDiceSchemaError();
    }

    public void pickDiceSchema(List action) {
        v.pickDiceSchema(action.subList(1,action.size()));
    }

    public void pickDiceSchemaError() {
        v.pickDiceSchemaError();
    }

    public void useToolCardAccepted() { v.useToolCardAccepted(); }

    public void useToolCardError() { v.useToolCardError(); }
}

