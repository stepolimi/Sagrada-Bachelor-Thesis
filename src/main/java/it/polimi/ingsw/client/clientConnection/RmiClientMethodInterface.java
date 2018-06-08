package it.polimi.ingsw.client.clientConnection;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiClientMethodInterface extends Remote{
    void updateText(String s) throws RemoteException;
    void printText(String s) throws RemoteException;
    void login(List action)throws RemoteException;
    void playerDisconnected(List action) throws RemoteException;
    void timerPing(List action)throws RemoteException;
    void createGame()throws RemoteException;
    void setSchemas(List action)throws RemoteException;
    void setPrivateCard(String privateCard)throws RemoteException;
    void setPublicObjectives(List action)throws RemoteException;
    void setToolCards(List action)throws RemoteException;
    void chooseSchema(List action) throws RemoteException;
    void setOpponentsSchemas(List action) throws RemoteException;
    void startRound() throws RemoteException;
    void startTurn(List action) throws RemoteException;
    void setActions(List action) throws RemoteException;
    void setDiceSpace(List action) throws RemoteException;
    void insertDiceAccepted() throws RemoteException;
    void draftDiceAccepted() throws RemoteException;
    void moveDiceAccepted() throws RemoteException;

    void pickDiceSpace(List action) throws RemoteException;
    void pickDiceSpaceError() throws RemoteException;

    void placeDiceSchema(List action) throws RemoteException;
    void placeDiceSchemaError() throws RemoteException;

    void pickDiceSchema(List action) throws RemoteException;
    void pickDiceSchemaError() throws RemoteException;

    void useToolCardAccepted(List action) throws RemoteException;
    void useToolCardError() throws RemoteException;

    void changeValueAccepted() throws RemoteException;
    void changeValueError() throws RemoteException;

    void placeDiceAccepted() throws RemoteException;

    void rollDiceAccepted(List action ) throws RemoteException;

    void swapDiceAccepted() throws RemoteException;

    void pickDiceRoundTrack(List action) throws RemoteException;
    void pickDiceRoundTrackError() throws RemoteException;

    void placeDiceRoundTrack(List action) throws RemoteException;

    void flipDiceAccepted(List action) throws RemoteException;

    void cancelUseToolCardAccepted(List action) throws RemoteException;

    void placeDiceSpace(List action) throws RemoteException;

    void placeDiceSpaceAccepted() throws RemoteException;

    void rollDiceSpaceAccepted(List action) throws RemoteException;
}
