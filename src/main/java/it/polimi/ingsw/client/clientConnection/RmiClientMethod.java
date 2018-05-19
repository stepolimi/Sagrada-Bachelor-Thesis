package it.polimi.ingsw.client.clientConnection;

import it.polimi.ingsw.client.view.ControllerClient;

import java.io.IOException;
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
        if(str.equals("Welcome") || str.equals("Login_error")) {
            try {
                controllerClient.login_resultRMI(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(str);
    }

    public void setView(ControllerClient controllerClient)
    {
        this.controllerClient = controllerClient;
    }

}
