package it.polimi.ingsw.server.connection.rmi;

import it.polimi.ingsw.client.clientConnection.rmi.RmiClientMethodInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerMethodInterface extends Remote {
    void login(RmiClientMethodInterface client, String nome) throws RemoteException;

    void disconnected(String name) throws RemoteException;

    void sendSchema(String schema, String name) throws RemoteException;

    void insertDice(String name, int indexDiceSpace, int row, int column) throws RemoteException;

    void useToolCard(String name, int toolNumber) throws RemoteException;

    void moveDice(String name, int oldRow, int oldColumn, int newRow, int newColumn) throws RemoteException;

    void sendEndTurn(String name) throws RemoteException;

    void draftDice(String name, int indexDiceSpace) throws RemoteException;

    void placeDice(String name, int row, int column) throws RemoteException;

    void changeValue(String name, String change) throws RemoteException;

    void rollDice(String name) throws RemoteException;

    void swapDice(String name, int numRound, int indexDice) throws RemoteException;

    void cancelUseToolCard(String name) throws RemoteException;

    void flipDice(String name) throws RemoteException;

    void placeDiceSpace(String name) throws RemoteException;

    void rollDiceSpace(String name) throws RemoteException;

    void swapDiceBag(String name) throws RemoteException;

    void chooseValue(String name, int value) throws RemoteException;

    void sendCustomSchema(String schema, String name) throws RemoteException;

    void ping() throws RemoteException;
}
