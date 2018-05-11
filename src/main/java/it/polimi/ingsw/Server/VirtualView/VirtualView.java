package it.polimi.ingsw.Server.VirtualView;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethodInterface;
import it.polimi.ingsw.Server.Model.game.GameMultiplayer;
import it.polimi.ingsw.Server.ServerConnection.MultiSocketServer;
import it.polimi.ingsw.Server.ServerConnection.RmiServerMethod;
import it.polimi.ingsw.Server.ServerConnection.SocketConnection;
import it.polimi.ingsw.Server.Model.game.Session;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VirtualView extends Observable implements Observer{
    private RmiServerMethod server;
    private MultiSocketServer socket;

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
       /* //this.ds = controller.getDiceSpace();
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
        }*/
       try {
           if (o.getClass() == Session.class) { sessionHandler(arg); }
           else if (o.getClass() == GameMultiplayer.class) { gameMultiplayerHandler(arg); }

       } catch(RemoteException ex) {
           System.out.println(ex.getMessage());
       }

    }

    private void sessionHandler(Object arg) throws RemoteException{
        /*notify:
            player joined, player left, new game cause timer, new game cause players
           */
        if (server.getClients().size() > 0) {
            for (RmiClientMethodInterface client : server.getClients()) {
                    client.updateText((String) arg);
            }
        }
        if (socket != null) {
            for (SocketConnection s : socket.getSocket())
                s.InviaInfo((String) arg);
        }
    }

    private void gameMultiplayerHandler(Object arg) throws RemoteException{
        /*notify:
            schemi creati
            */
        if (server.getClients().size() > 0) {
            for (RmiClientMethodInterface client : server.getClients()) {
                    client.updateText((String) arg);
            }
        }
        if (socket != null) {
            for (SocketConnection s : socket.getSocket())
                s.InviaInfo((String) arg);
        }
    }


}
