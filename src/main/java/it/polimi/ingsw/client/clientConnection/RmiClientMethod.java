package it.polimi.ingsw.client.clientConnection;


import it.polimi.ingsw.client.view.Handler;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RmiClientMethod extends UnicastRemoteObject implements RmiClientMethodInterface {
    Handler hand;
    public RmiClientMethod(Handler hand) throws RemoteException
    {
        this.hand = hand;
    }

    public void updateText(String s) throws RemoteException {}

    public void printText(String str)
    {
        hand.deliverGI(str);
        System.out.println(str);
    }


}

