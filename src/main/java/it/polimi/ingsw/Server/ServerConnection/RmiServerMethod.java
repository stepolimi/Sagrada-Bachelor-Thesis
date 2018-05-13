package it.polimi.ingsw.Server.ServerConnection;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethodInterface;
import it.polimi.ingsw.Server.VirtualView.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

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
        if(connection.checkUsername(name)) {
            connection.getUsers().put(user, name);

            System.out.println(name + " si è connesso");
            try {
                client.printText("Welcome");
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }else {
            try {
                client.printText("Login_error");
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    /*public void publish(String str) throws RemoteException {
        if(!clients.isEmpty())
        {
            for(RmiClientMethodInterface client:clients.keySet()) {
                try{
                    client.updateText(str);
                    client.printText(str);
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
        connection.sendMessage(name+" si è disconnesso");
        connection.sendMessage("Numero utenti ancora connessi:"+connection.nConnection());
       /* String str = clients.get(client);
        this.clients.remove(client);
        this.publish(str+" si è disconnesso");
        */
    }
}
