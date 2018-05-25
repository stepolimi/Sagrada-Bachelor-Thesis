package it.polimi.ingsw.client.clientConnection;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiClientMethodInterface extends Remote{
    public void updateText(String s) throws RemoteException;
    public void printText(String s) throws RemoteException;
    public void login(List action)throws RemoteException;
    public void playerDisconnected(List action) throws RemoteException;
    public void timerPing(List action)throws RemoteException;
    public void createGame()throws RemoteException;
    public void setSchemas(List action)throws RemoteException;
    public void setPrivateCard(List action)throws RemoteException;
    public void setPublicObjectives(List action)throws RemoteException;
    public void setToolCards(List action)throws RemoteException;
    public void chooseSchema(List action) throws RemoteException;
}
