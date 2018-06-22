package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.client.clientConnection.RmiClientMethodInterface;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameCreationMessages.END_TURN;
import static it.polimi.ingsw.costants.GameCreationMessages.PICK_DICE;
import static it.polimi.ingsw.costants.LoginMessages.DISCONNECTED;
import static it.polimi.ingsw.costants.LoginMessages.LOGIN;
import static it.polimi.ingsw.server.serverCostants.Constants.DRAFT_DICE;
import static it.polimi.ingsw.server.serverCostants.Constants.USE_TOOL_CARD;

public class RmiServerMethod implements RmiServerMethodInterface {
    private HashMap<RmiClientMethodInterface,String > clients = new HashMap<RmiClientMethodInterface,String>();
    private VirtualView virtual;
    private Connected connection;

    public RmiServerMethod(VirtualView virtual,Connected connection) throws RemoteException {
        this.virtual = virtual;
        this.connection = connection;
    }

    public boolean login(RmiClientMethodInterface client,String name) {
        RmiServerConnection user = new RmiServerConnection(client,this);
        List action = new ArrayList();
        action.add(LOGIN);
        action.add(name);
        System.out.println(name + "'s trying to connect with rmi:");
        if(connection.checkUsername(name)) {
            connection.getUsers().put(user, name);
            virtual.forwardAction(action);
        }else {
            try {
                client.loginError("username");
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    public void disconnected(RmiClientMethodInterface client) {
        RmiServerConnection c = new RmiServerConnection(client,this);
        String name = connection.remove(c);
        if(name != null) {
            List action = new ArrayList();
            action.add(DISCONNECTED);
            action.add(name);
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
        action.add(PICK_DICE);
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


}
