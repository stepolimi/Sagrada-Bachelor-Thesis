package it.polimi.ingsw.client.clientConnection;


import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.server.serverConnection.RmiServerMethodInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RmiConnection implements Connection {
    RmiServerMethodInterface server;
    RmiClientMethod client;
    Handler hand; // used to manage graphic
    public RmiConnection(Handler hand)
    {
        this.hand= hand;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",1099);
            client = new RmiClientMethod(hand);
            server = (RmiServerMethodInterface) registry.lookup("RmiServerMethodInterface");
        }catch(RemoteException e){
            System.out.println(e.getMessage());
        } catch(NotBoundException e3){
            System.out.println(e3.getMessage());
        }

    }


    public void sendSchema(String str) {
        try {
            List action = new ArrayList();
            action.add("ChooseSchema");
            action.add(hand.getView().getName());
            action.add(str);
            server.forwardAction(action,client);
        }catch(RemoteException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void login(String nickname) {
        try {
            server.login(client, nickname);
        }catch(RemoteException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect() {
        try {
            server.disconnected(client);
        }catch(RemoteException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void insertDice(final int indexDiceSpace, final int row, final int column) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    server.insertDice(indexDiceSpace,row,column);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void sendEndTurn() {
        try {
            server.sendEndTurn();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}

