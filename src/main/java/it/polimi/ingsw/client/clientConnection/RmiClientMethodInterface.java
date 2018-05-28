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
    public void setOpponentsSchemas(List action) throws RemoteException;
    void startRound(List action) throws RemoteException;
    void startTurn(List action) throws RemoteException;
    void setActions(List action) throws RemoteException;
    void setDiceSpace(List action) throws RemoteException;
    void insertDiceAccepted(List action) throws RemoteException;

    void pickDiceSpace(List action) throws RemoteException;
    void pickDiceSpaceError(List action) throws RemoteException;

    void placeDiceSchema(List action) throws RemoteException;
    void placeDiceSchemaError(List action) throws RemoteException;
}
