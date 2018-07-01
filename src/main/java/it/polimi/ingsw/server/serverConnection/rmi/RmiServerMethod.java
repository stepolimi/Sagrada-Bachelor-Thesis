package it.polimi.ingsw.server.serverConnection.rmi;

import it.polimi.ingsw.client.clientConnection.rmi.RmiClientMethodInterface;
import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.MessageConstants.*;

public class RmiServerMethod implements RmiServerMethodInterface {
    private VirtualView virtual;
    private Connected connection;
    private String nickname;

    public RmiServerMethod(VirtualView virtual,Connected connection) throws RemoteException {
        this.virtual = virtual;
        this.connection = connection;
    }

    public boolean login(RmiClientMethodInterface client,String name) {
        List action = new ArrayList();
        Log.getLogger().addLog(name + "'s trying to connect with rmi:", Level.INFO,this.getClass().getName(),"login");

        if(connection.checkUsername(name)) {
            RmiServerConnection user = new RmiServerConnection(client,this,name);
            nickname = name;
            connection.addPlayer(name,user);
            action.add(LOGIN);
            action.add(name);
            virtual.forwardAction(action);
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
            List action = new ArrayList();
            action.add(DISCONNECTED);
            action.add(nickname);
            virtual.forwardAction(action);
        }
    }

    public void sendSchema(String schema, String name) {
        List action = new ArrayList();
        action.add(CHOOSE_SCHEMA);
        action.add(name);
        action.add(schema);
        virtual.forwardAction(action);
    }

    public void insertDice(int indexDiceSpace, int row, int column) {
        List action = new ArrayList();
        action.add(INSERT_DICE);
        action.add(((Integer)indexDiceSpace).toString());
        action.add(((Integer)row).toString());
        action.add(((Integer)column).toString());
        virtual.forwardAction(action);
    }

    public void useToolCard(int toolNumber) {
        List action = new ArrayList();
        action.add(USE_TOOL_CARD);
        action.add(((Integer)toolNumber).toString());
        virtual.forwardAction(action);
    }

    public void moveDice(int oldRow,int oldColumn, int newRow, int newColumn){
        List action = new ArrayList();
        action.add(MOVE_DICE);
        action.add(((Integer)oldRow).toString());
        action.add(((Integer)oldColumn).toString());
        action.add(((Integer)newRow).toString());
        action.add(((Integer)newColumn).toString());
        virtual.forwardAction(action);
    }


    public void sendEndTurn() {
        List action = new ArrayList();
        action.add(END_TURN);
        virtual.forwardAction(action);
    }

    public void draftDice(int indexDiceSpace) {
        List action = new ArrayList();
        action.add(DRAFT_DICE);
        action.add(((Integer)indexDiceSpace).toString());
        virtual.forwardAction(action);
    }

    public void placeDice(int row, int column) {
        List action = new ArrayList();
        action.add(PLACE_DICE);
        action.add(((Integer)row).toString());
        action.add(((Integer)column).toString());
        virtual.forwardAction(action);
    }

    public void changeValue(String change) {
        List action = new ArrayList();
        action.add(CHANGE_VALUE);
        action.add(change);
        virtual.forwardAction(action);
    }

    public void rollDice() {
        List action = new ArrayList();
        action.add(ROLL_DICE);
        virtual.forwardAction(action);
    }

    public void swapDice(int numRound, int indexDice){
        List action = new ArrayList();
        action.add(SWAP_DICE);
        action.add(((Integer)numRound).toString());
        action.add(((Integer)indexDice).toString());
        virtual.forwardAction(action);
    }

    public void cancelUseToolCard() {
        List action = new ArrayList();
        action.add(CANCEL_USE_TOOL_CARD);
        virtual.forwardAction(action);
    }

    public void flipDice()  {
        List action = new ArrayList();
        action.add(FLIP_DICE);
        virtual.forwardAction(action);
    }

    public void placeDiceSpace() {
        List action = new ArrayList();
        action.add(PLACE_DICE_SPACE);
        virtual.forwardAction(action);
    }

    public void rollDiceSpace() {
        List action = new ArrayList();
        action.add(ROLL_DICE_SPACE);
        virtual.forwardAction(action);
    }

    public void swapDiceBag() {
        List action = new ArrayList();
        action.add(SWAP_DICE_BAG);
        virtual.forwardAction(action);
    }

    public void chooseValue(int value) {
        List action = new ArrayList();
        action.add(CHOOSE_VALUE);
        action.add(((Integer)value).toString());
        virtual.forwardAction(action);
    }

    public void sendCustomSchema(String schema, String name) {
        List action = new ArrayList();
        action.add(CUSTOM_SCHEMA);
        action.add(name);
        action.add(schema);
        virtual.forwardAction(action);
    }

    public void ping() {

    }


}


