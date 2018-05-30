package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.client.clientConnection.RmiClientMethodInterface;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.ingsw.costants.GameCreationMessages.END_TURN;
import static it.polimi.ingsw.costants.GameCreationMessages.PICK_DICE;
import static it.polimi.ingsw.costants.LoginMessages.LOGIN_ERROR;

public class RmiServerMethod implements RmiServerMethodInterface {
    private HashMap<RmiClientMethodInterface,String > clients = new HashMap<RmiClientMethodInterface,String>();
    private VirtualView virtual;
    private Connected connection;

    public RmiServerMethod(VirtualView virtual,Connected connection) throws RemoteException {
        this.virtual = virtual;
        this.connection = connection;
    }

    public boolean login(RmiClientMethodInterface client,String name) {
        // controllerò se non ci sono username uguali
        RmiServerConnection user = new RmiServerConnection(client,this);
        List action = new ArrayList();
        action.add("Login");
        action.add(name);
        System.out.println(name + "'s trying to connect with rmi:");
        if(connection.checkUsername(name)) {
            connection.getUsers().put(user, name);
            virtual.forwardAction(action);
        }else {
            try {
                action.clear();
                action.add(LOGIN_ERROR);
                action.add("username");
                client.login(action);
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    /*public void publish(String str) throws RemoteException {
        if(!clients.isEmpty())
        {
            for(RmiClientMethodInterface RmiServerConnection:clients.keySet()) {
                try{
                    RmiServerConnection.updateText(str);
                    RmiServerConnection.printText(str);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    public HashMap<RmiClientMethodInterface,String> getClients()
    {
        return this.clients;
    }
*/
    public void forwardAction(List action,RmiClientMethodInterface client) {
        virtual.forwardAction(action);
    }

    public void forwardAction(String str,RmiClientMethodInterface client) throws RemoteException {
        if(clients.containsKey(client)) {
            ArrayList action = new ArrayList();
            StringTokenizer token = new StringTokenizer(str);
            while (token.hasMoreTokens())
                action.add(token.nextToken());

            virtual.forwardAction(action);
        }
    }

    public void disconnected(RmiClientMethodInterface client) {
        RmiServerConnection c = new RmiServerConnection(client,this);
        String name = connection.remove(c);
        if(name != null) {
            List action = new ArrayList();
            action.add("Disconnected");
            action.add(name);
            virtual.forwardAction(action);
        }
    }

    public void insertDice(int indexDiceSpace, int row, int column) {
        List action = new ArrayList();
        action.add(PICK_DICE);
        action.add(((Integer)indexDiceSpace).toString());
        action.add(((Integer)row).toString());
        action.add(((Integer)column).toString());
        virtual.forwardAction(action);
    }

    public void sendEndTurn() {
        List action = new ArrayList();
        action.add(END_TURN);
        virtual.forwardAction(action);
    }

}
