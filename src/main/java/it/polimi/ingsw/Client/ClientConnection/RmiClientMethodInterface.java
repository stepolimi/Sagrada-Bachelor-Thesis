package it.polimi.ingsw.Client.ClientConnection;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientMethodInterface extends Remote{
    public void updateText(String s) throws RemoteException;
    public String getName() throws RemoteException;
}
