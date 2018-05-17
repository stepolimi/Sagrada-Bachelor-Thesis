package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethodInterface;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import static it.polimi.ingsw.costants.LoginMessages.loginError;

public class RmiServerMethod extends UnicastRemoteObject  implements RmiServerMethodInterface {
    private HashMap<RmiClientMethodInterface,String > clients = new HashMap<RmiClientMethodInterface,String>();
    private VirtualView virtual;
    private Connected connection;
    public RmiServerMethod(VirtualView virtual,Connected connection) throws RemoteException
    {
        this.virtual = virtual;
        this.connection = connection;
    }

    public boolean login(RmiClientMethodInterface client,String name) {
        // controllerò se non ci sono username uguali
        Client user = new Client(client);
        List action = new ArrayList();
        action.add("Login");
        action.add(name);
        if(connection.checkUsername(name)) {
            connection.getUsers().put(user, name);
            virtual.forwardAction(action);
            System.out.println(name + " si è connesso");
        }else {
            try {
                client.printText(loginError);
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    /*public void publish(String str) throws RemoteException {
        if(!clients.isEmpty())
        {
            for(RmiClientMethodInterface Client:clients.keySet()) {
                try{
                    Client.updateText(str);
                    Client.printText(str);
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
    public void forwardAction(ArrayList action,RmiClientMethodInterface client) {
        if(clients.containsKey(client))
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

    public void disconnected(RmiClientMethodInterface client) throws RemoteException {

        Client c = new Client(client);
        String name = connection.remove(c);
       /* String str = clients.get(Client);
        this.clients.remove(Client);
        this.publish(str+" si è disconnesso");
        */
    }
}
