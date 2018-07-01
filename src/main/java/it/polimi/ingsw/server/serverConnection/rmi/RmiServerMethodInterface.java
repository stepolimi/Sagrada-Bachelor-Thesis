package it.polimi.ingsw.server.serverConnection.rmi;

import it.polimi.ingsw.client.clientConnection.rmi.RmiClientMethodInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerMethodInterface extends Remote {
    boolean login(RmiClientMethodInterface client, String nome) throws RemoteException;

    void disconnected(String name) throws RemoteException;

    void sendSchema(String schema, String name) throws RemoteException;

    void insertDice(int indexDiceSpace, int row, int column) throws RemoteException;

    void useToolCard(int toolNumber) throws RemoteException;

    void moveDice(int oldRow, int oldColumn, int newRow, int newColumn) throws RemoteException;

    void sendEndTurn() throws RemoteException;

    void draftDice(int indexDiceSpace) throws RemoteException;

    void placeDice(int row, int column) throws RemoteException;

    void changeValue(String change) throws RemoteException;

    void rollDice() throws RemoteException;

    void swapDice(int numRound, int indexDice) throws RemoteException;

    void cancelUseToolCard() throws RemoteException;

    void flipDice() throws RemoteException;

    void placeDiceSpace() throws RemoteException;

    void rollDiceSpace() throws RemoteException;

    void swapDiceBag() throws RemoteException;

    void chooseValue(int value) throws RemoteException;

    void sendCustomSchema(String schema, String name) throws RemoteException;

    void ping() throws RemoteException;
}
