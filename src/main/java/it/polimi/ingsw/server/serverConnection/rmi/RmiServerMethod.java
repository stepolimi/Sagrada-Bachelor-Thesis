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
        virtual.sendSchema(schema, name);
    }

    public void insertDice(String name, int indexDiceSpace, int row, int column) {
        virtual.insertDice(name, indexDiceSpace,row,column);
    }

    public void useToolCard(String name, int toolNumber) {
        virtual.useToolCard(name, toolNumber);
    }

    public void moveDice(String name, int oldRow,int oldColumn, int newRow, int newColumn){
        virtual.moveDice(name,oldRow,oldColumn,newRow,newColumn);
    }


    public void sendEndTurn(String name) {
        virtual.sendEndTurn(name);
    }

    public void draftDice(String name, int indexDiceSpace) {
        virtual.draftDice(name, indexDiceSpace);
    }

    public void placeDice(String name, int row, int column) {
        virtual.placeDice(name, row,column);
    }

    public void changeValue(String name, String change) {
        virtual.changeValue(name, change);
    }

    public void rollDice(String name) { virtual.rollDice(name); }

    public void swapDice(String name, int numRound, int indexDice){
        virtual.swapDice(name, numRound,indexDice);
    }

    public void cancelUseToolCard(String name) {
        virtual.cancelUseToolCard(name);
    }

    public void flipDice(String name)  {
        virtual.flipDice(name);
    }

    public void placeDiceSpace(String name) {
        virtual.placeDiceSpace(name);
    }

    public void rollDiceSpace(String name) {
        virtual.rollDiceSpace(name);
    }

    public void swapDiceBag(String name) {
        virtual.swapDiceBag(name);
    }

    public void chooseValue(String name, int value) {
        virtual.chooseValue(name,value);
    }

    public void sendCustomSchema(String schema, String name) {
        virtual.sendCustomSchema(schema,name);
    }

    public void ping() {

    }


}


