package it.polimi.ingsw.client.clientConnection;


import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.client.view.View;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.LoginMessages.loginError;
import static it.polimi.ingsw.costants.LoginMessages.loginSuccessful;


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
        if(action.get(0).equals(loginSuccessful)) {
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

    public void setPrivateCard(List action)throws RemoteException{
        v.setPrivateCard((String)action.get(1));
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

    public void chooseSchema()
    {
        //v.chooseSchema
    }

    public void startTurn()
    {
        // v.startTurn();
    }

}

