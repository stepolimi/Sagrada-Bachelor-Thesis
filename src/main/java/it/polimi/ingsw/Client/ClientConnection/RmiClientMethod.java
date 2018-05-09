package it.polimi.ingsw.Client.ClientConnection;

import it.polimi.ingsw.Client.View.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClientMethod extends UnicastRemoteObject implements RmiClientMethodInterface {
    View v;
    String name;
    public RmiClientMethod(String name) throws RemoteException
    {
        this.name = name;
    }


    public void updateText(String s) throws RemoteException {
        v.text.setText(s);
        System.out.println(s);
    }


    public String getName() throws RemoteException {
        return this.name;
    }

    public void setView(View v)
    {
        this.v = v;
    }

}
