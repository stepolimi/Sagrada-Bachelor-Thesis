package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.client.clientConnection.RmiClientMethodInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RmiServerMethodInterface extends Remote {
    public boolean login(RmiClientMethodInterface client,String nome) throws RemoteException;
    // public void publish(String str) throws RemoteException;
    public void forwardAction(List action, RmiClientMethodInterface client) throws RemoteException;
    public void forwardAction(String action,RmiClientMethodInterface client) throws RemoteException;
    public void disconnected(RmiClientMethodInterface client) throws RemoteException;

}
