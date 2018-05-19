package it.polimi.ingsw.client.clientConnection;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientMethodInterface extends Remote{
    public void updateText(String s) throws RemoteException;
    public void printText(String s) throws RemoteException;
}
