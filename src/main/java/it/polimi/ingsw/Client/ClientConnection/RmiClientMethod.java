package it.polimi.ingsw.Client.ClientConnection;

import it.polimi.ingsw.Client.View.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClientMethod extends UnicastRemoteObject implements RmiClientMethodInterface {
    View v;

    public RmiClientMethod() throws RemoteException
    { }


    public void updateText(String s) throws RemoteException {
        v.text.setText(s);
    }

    public void printText(String str)
    {
        System.out.println(str);
    }

    public void setView(View v)
    {
        this.v = v;
    }

}
