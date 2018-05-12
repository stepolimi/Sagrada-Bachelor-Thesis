package it.polimi.ingsw.Client.ClientConnection;


import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.Server.ServerConnection.RmiServerMethodInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiConnection implements Connection {
    RmiServerMethodInterface server;
    RmiClientMethod client;
    View v;
    public RmiConnection(View v)
    {
        this.v = v;
        try {
            client = new RmiClientMethod();
            client.setView(v);
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

    public void login() {
        try {
            server.login(client,v.getName());
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

