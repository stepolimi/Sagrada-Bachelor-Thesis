package it.polimi.ingsw.VirtualView;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethodInterface;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Model.DiceBag;
import it.polimi.ingsw.Model.DiceSpace;
import it.polimi.ingsw.ServerConnection.MultiSocketServer;
import it.polimi.ingsw.ServerConnection.RmiServerMethod;
import it.polimi.ingsw.ServerConnection.SocketConnection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VirtualView extends Observable implements Observer{
private DiceSpace ds;
private DiceBag bag;
private ServerController controller;
private RmiServerMethod server;
private MultiSocketServer socket;
public VirtualView(DiceSpace ds,DiceBag bag,ServerController controller)
{
 this.ds = ds;
 this.bag = bag;
 this.controller = controller;
 this.addObserver(controller);
}

    public void setMultipleServerSocket(MultiSocketServer socket){
        this.socket = socket;
    }
public void forwardAction(ArrayList action)
{
 setChanged();
 notifyObservers(action);
}
public void setServer(RmiServerMethod server)
{
    this.server = server;
}

    public void update(Observable o, Object arg) {
        this.ds = controller.getDiceSpace();
        if(server.getClients().size()>0) {
            for (RmiClientMethodInterface client : server.getClients()) {
                try {
                    client.updateText(ds.toString());
                } catch (RemoteException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        if(socket!=null)
        {
            for(SocketConnection s:socket.getSocket())
                s.InviaInfo(ds.toString());
        }
    }


}