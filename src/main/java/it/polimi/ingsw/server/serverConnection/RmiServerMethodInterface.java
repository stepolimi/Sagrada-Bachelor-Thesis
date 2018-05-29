package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.client.clientConnection.RmiClientMethodInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RmiServerMethodInterface extends Remote {
    boolean login(RmiClientMethodInterface client,String nome) throws RemoteException;
    // public void publish(String str) throws RemoteException;
    void forwardAction(List action, RmiClientMethodInterface client) throws RemoteException;
    void forwardAction(String action,RmiClientMethodInterface client) throws RemoteException;
    void disconnected(RmiClientMethodInterface client) throws RemoteException;
    void insertDice(int indexDiceSpace, int row, int column) throws RemoteException;
    void sendEndTurn() throws RemoteException;

}
