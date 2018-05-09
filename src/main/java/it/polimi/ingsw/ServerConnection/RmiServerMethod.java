package it.polimi.ingsw.ServerConnection;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethodInterface;
import it.polimi.ingsw.VirtualView.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;

public class RmiServerMethod extends UnicastRemoteObject  implements RmiServerMethodInterface {
    private Vector<RmiClientMethodInterface> clients = new Vector<RmiClientMethodInterface>();
    private VirtualView virtual;

    public RmiServerMethod(VirtualView virtual) throws RemoteException
    {
        this.virtual = virtual;
    }

    public boolean login(RmiClientMethodInterface client) throws RemoteException {
        // dobbiamo controllare che non ci siano 2 user =
        clients.add(client);
        System.out.println("Connesso");
        return true;
    }

    public void publish(String str) throws RemoteException {
        for(RmiClientMethodInterface client:clients)
        {
            client.updateText(str);
        }
    }

    public Vector<RmiClientMethodInterface> getClients()
    {
        return this.clients;
    }

    public void forwardAction(ArrayList action) throws RemoteException {
        virtual.forwardAction(action);
    }

    public void disconnected(RmiClientMethodInterface client) throws RemoteException {
        this.clients.remove(client);
    }
}
