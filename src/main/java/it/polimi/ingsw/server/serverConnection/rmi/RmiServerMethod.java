package it.polimi.ingsw.server.serverConnection.rmi;

import it.polimi.ingsw.client.clientConnection.rmi.RmiClientMethodInterface;
import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.rmi.RemoteException;
import java.util.logging.Level;

public class RmiServerMethod implements RmiServerMethodInterface {
    private VirtualView virtual;
    private Connected connection;
    private String nickname;

    public RmiServerMethod(VirtualView virtual,Connected connection) throws RemoteException {
        this.virtual = virtual;
        this.connection = connection;
    }

    public boolean login(RmiClientMethodInterface client,String name) {
        Log.getLogger().addLog(name + "'s trying to connect with rmi:", Level.INFO,this.getClass().getName(),"login");

        if(connection.checkUsername(name)) {
            RmiServerConnection user = new RmiServerConnection(client,this,name);
            nickname = name;
            connection.addPlayer(name,user);
            virtual.login(name);
        }else {
            try {
                client.loginError("username");
            } catch (RemoteException e) {
                Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),"login");
            }
        }
        return true;
    }

    public void disconnected(String nickname) {
        if(connection.removePlayer(nickname)){
            virtual.disconnected(nickname);
        }
    }

    public void sendSchema(String schema, String name) {
        virtual.sendSchema(schema,name);
    }

    public void insertDice(int indexDiceSpace, int row, int column) {
        virtual.insertDice(indexDiceSpace,row,column);
    }

    public void useToolCard(int toolNumber) {
        virtual.useToolCard(toolNumber);
    }

    public void moveDice(int oldRow,int oldColumn, int newRow, int newColumn){
        virtual.moveDice(oldRow,oldColumn,newRow,newColumn);
    }


    public void sendEndTurn() {
        virtual.sendEndTurn();
    }

    public void draftDice(int indexDiceSpace) {
        virtual.draftDice(indexDiceSpace);
    }

    public void placeDice(int row, int column) {
        virtual.placeDice(row,column);
    }

    public void changeValue(String change) {
        virtual.changeValue(change);
    }

    public void rollDice() {
        virtual.rollDice();
    }

    public void swapDice(int numRound, int indexDice){
        virtual.swapDice(numRound,indexDice);
    }

    public void cancelUseToolCard() {
        virtual.cancelUseToolCard();
    }

    public void flipDice()  {
        virtual.flipDice();
    }

    public void placeDiceSpace() {
        virtual.placeDiceSpace();
    }

    public void rollDiceSpace() {
        virtual.rollDiceSpace();
    }

    public void swapDiceBag() {
        virtual.swapDiceBag();
    }

    public void chooseValue(int value) {
        virtual.chooseValue(value);
    }

    public void sendCustomSchema(String schema, String name) {
        virtual.sendCustomSchema(schema,name);
    }

    public void ping() {

    }


}


