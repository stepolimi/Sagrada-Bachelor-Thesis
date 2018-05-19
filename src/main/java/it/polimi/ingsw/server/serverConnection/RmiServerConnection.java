package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethodInterface;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.costants.GameCreationMessages.*;
import static it.polimi.ingsw.costants.LoginMessages.loginError;
import static it.polimi.ingsw.costants.LoginMessages.loginSuccessful;
import static it.polimi.ingsw.costants.LoginMessages.startingGameMsg;

public class RmiServerConnection implements Connection{
    RmiClientMethodInterface client;

    public RmiServerConnection(RmiClientMethodInterface client)
    {
        this.client = client;
    }


    public void sendMessage(List action) {
        try {
            if((action.get(0)).equals(loginError) || (action.get(0)).equals(loginSuccessful)) { client.printText((String)action.get(0)); }              //it will call his method and send action
            else if(action.get(0).equals(startingGameMsg)) { client.printText((String)action.get(1)); }                                                 //it will call his method and send action
            else if(action.get(0).equals(setPrivateCard)) { client.printText((String)action.get(1)); }                                                  //it will call his method and send action
            else if(action.get(0).equals(setSchemas)) { client.printText((String)action.get(1)); }                                                      //it will call his method and send action
            else if(action.get(0).equals(setPublicObjectives)) { client.printText((String)action.get(1));}                                              //it will call his method and send action
            else if(action.get(0).equals(setToolCards)) { client.printText((String)action.get(1));}                                                     //it will call his method and send action

        }catch(RemoteException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof RmiServerConnection) )
            return false;

        RmiServerConnection cli = (RmiServerConnection) obj;
        System.out.println("Ugualiiiiiii");
        System.out.println(this.client.equals(cli.client));
       return (this.client.equals(cli.client)) ;
    }
}
