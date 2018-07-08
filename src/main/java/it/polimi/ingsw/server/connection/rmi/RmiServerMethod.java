package it.polimi.ingsw.server.connection.rmi;

import it.polimi.ingsw.client.clientConnection.rmi.RmiClientMethodInterface;
import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.connection.Connected;
import it.polimi.ingsw.server.virtual.view.VirtualView;

import java.rmi.RemoteException;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.CONNECTING_WITH_RMI;
import static it.polimi.ingsw.server.costants.LogConstants.RMI_SERVER_METHOD_LOGIN;

public class RmiServerMethod implements RmiServerMethodInterface {
    private final VirtualView virtual;
    private final Connected connection;

    public RmiServerMethod(VirtualView virtual,Connected connection){
        this.virtual = virtual;
        this.connection = connection;
    }


    /**
     * @param client is client connection
     * @param name is the name of player
     */
    public void login(RmiClientMethodInterface client,String name) {
        Log.getLogger().addLog(name + CONNECTING_WITH_RMI, Level.INFO,this.getClass().getName(),RMI_SERVER_METHOD_LOGIN);

        if(connection.checkUsername(name)) {
            RmiServerConnection user = new RmiServerConnection(client,this,name);
            connection.addPlayer(name,user);
            virtual.login(name);
        }else {
            try {
                client.loginError("username");
            } catch (RemoteException e) {
                Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),RMI_SERVER_METHOD_LOGIN);
            }
        }
    }

    /**
     * disconnect the player
     */
    public void disconnected(String nickname) {
        if(connection.removePlayer(nickname)){
            virtual.disconnected(nickname);
        }
    }
    /**
     * @param schema is the name of scheme
     * @param name is the name of player
     */
    public void sendSchema(String schema, String name) {
        virtual.sendSchema(schema, name);
    }

    /**
     * used to insert dice to scheme from diceSpace
     * @param name is the name of player
     * @param indexDiceSpace is index of dice space
     * @param row is index of row
     * @param column is index of column
     */
    public void insertDice(String name, int indexDiceSpace, int row, int column) {
        virtual.insertDice(name, indexDiceSpace,row,column);
    }

    /**
     * invoked when you want use tool card
     * @param name is the name of player
     * @param toolNumber is tool card's number
     */
    public void useToolCard(String name, int toolNumber) {
        virtual.useToolCard(name, toolNumber);
    }

    /**
     * invoked when you want move dice in scheme
     * @param name is the name of player
     * @param oldRow is the row from take dice
     * @param oldColumn is the column from take dice
     * @param newRow is the row to move dice
     * @param newColumn is the column to move dice
     */
    public void moveDice(String name, int oldRow,int oldColumn, int newRow, int newColumn){
        virtual.moveDice(name,oldRow,oldColumn,newRow,newColumn);
    }


    public void sendEndTurn(String name) {
        virtual.sendEndTurn(name);
    }

    /**
     * is invoked when use draft dice
     * @param name is the name of player
     * @param indexDiceSpace is index of dice space
     */
    public void draftDice(String name, int indexDiceSpace) {
        virtual.draftDice(name, indexDiceSpace);
    }

    /**
     * is invoked when use place dice
     * @param name is the name of player
     * @param row is row index of scheme
     * @param column is column index of scheme
     */
    public void placeDice(String name, int row, int column) {
        virtual.placeDice(name, row,column);
    }


    /**
     * is invoked when use changeValue
     * @param name is the name of player
     * @param change is "decrement" or "increment"
     */
    public void changeValue(String name, String change) {
        virtual.changeValue(name, change);
    }

    /**
     * is invoked when use roll dice
     * @param name is the name of player
     */
    public void rollDice(String name) { virtual.rollDice(name); }

    /**
     * take a dice from round track
     * @param name is the name of player
     * @param numRound is the number of round
     * @param indexDice is index of dice
     */
    public void swapDice(String name, int numRound, int indexDice){
        virtual.swapDice(name, numRound,indexDice);
    }

    /**
     * invoked when use cancel tool card
     * @param name is the name of player
     */
    public void cancelUseToolCard(String name) {
        virtual.cancelUseToolCard(name);
    }

    /**
     * turn to opposite face of dice
     * @param name is the name of player
     */
    public void flipDice(String name)  {
        virtual.flipDice(name);
    }

    /**
     * place dices in dice space
     * @param name is the name of player
     */
    public void placeDiceSpace(String name) {
        virtual.placeDiceSpace(name);
    }

    /**
     * roll dices in dice space
     * @param name is the name of player
     */
    public void rollDiceSpace(String name) {
        virtual.rollDiceSpace(name);
    }

    /**
     * exchange dice with dice bag
     * @param name is the name of player
     */
    public void swapDiceBag(String name) {
        virtual.swapDiceBag(name);
    }

    /**
     * is invoked when choose value of dice
     * @param name is the name of player
     * @param value is the new value of dice
     */
    public void chooseValue(String name, int value) {
        virtual.chooseValue(name,value);
    }

    /**
     * send custom scheme to server
     * @param schema is the name of custom schema
     * @param name is the name of player
     */
    public void sendCustomSchema(String schema, String name) {
        virtual.sendCustomSchema(schema,name);
    }

    /**
     * check connection
     */
    public void ping() {
        //is used by clients for verify that the server is still online. If it is not, a RemoteException is thrown.
    }


}


