package it.polimi.ingsw.Client.ClientConnection;


import it.polimi.ingsw.Client.View.ControllerClient;
import it.polimi.ingsw.server.serverConnection.RmiServerMethodInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiConnection implements Connection {
    RmiServerMethodInterface server;
    RmiClientMethod client;
    ControllerClient controllerClient;
    public RmiConnection(ControllerClient controllerClient)
    {
        this.controllerClient = controllerClient;
        try {
            client = new RmiClientMethod();
            client.setView(controllerClient);
            server = (RmiServerMethodInterface) Naming.lookup("rmi://127.0.0.1/myabc");
        }catch(RemoteException e){
            System.out.println(e.getMessage());
        }
        catch(MalformedURLException e2){
            System.out.println(e2.getMessage());
        }
        catch(NotBoundException e3){
            System.out.println(e3.getMessage());
        }

    }


    public void sendMessage(String str) {
        try {
            server.forwardAction(str,client);
        }catch(RemoteException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void login(String nickname) {
        try {
            server.login(client, nickname);
        }catch(RemoteException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect() {
        try {
            server.disconnected(client);
        }catch(RemoteException e)
        {
            System.out.println(e.getMessage());
        }
    }
}

