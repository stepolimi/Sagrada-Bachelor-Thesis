package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethodInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RmiServerMethodInterface extends Remote {
    public boolean login(RmiClientMethodInterface client,String nome) throws RemoteException;
    // public void publish(String str) throws RemoteException;
    public void forwardAction(ArrayList action,RmiClientMethodInterface client) throws RemoteException;
    public void forwardAction(String action,RmiClientMethodInterface client) throws RemoteException;
    public void disconnected(RmiClientMethodInterface client) throws RemoteException;

}
