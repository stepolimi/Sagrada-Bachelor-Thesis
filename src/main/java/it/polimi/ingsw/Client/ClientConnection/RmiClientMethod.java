package it.polimi.ingsw.Client.ClientConnection;

import it.polimi.ingsw.Client.View.ControllerClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClientMethod extends UnicastRemoteObject implements RmiClientMethodInterface {
        ControllerClient controllerClient;
    public RmiClientMethod() throws RemoteException
    { }


    public void updateText(String s) throws RemoteException {
        controllerClient.setText(s);
    }

    public void printText(String str)
    {
        System.out.println(str);
    }

    public void setView(ControllerClient controllerClient)
    {
        this.controllerClient = controllerClient;
    }

}
