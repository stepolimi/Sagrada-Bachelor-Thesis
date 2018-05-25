package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.client.clientConnection.RmiClientMethodInterface;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.costants.GameCreationMessages.*;
import static it.polimi.ingsw.costants.LoginMessages.*;

public class RmiServerConnection implements Connection{
    RmiClientMethodInterface client;
    RmiServerMethod serverMethod;

    public RmiServerConnection(RmiClientMethodInterface client, RmiServerMethod serverMethod) {
        this.client = client;
        this.serverMethod = serverMethod;
    }

    public void sendMessage(List action) {
        try {

            if((action.get(0)).equals(loginSuccessful)) {
                client.login(action);
            }else if((action.get(0)).equals(loginError) ){
                client.login(action);
            }else if(action.get(0).equals(logout)){
                client.playerDisconnected(action);
            } else if(action.get(0).equals(timerPing)) {
                client.timerPing(action);
            } else if(action.get(0).equals(startingGameMsg)) {
                client.createGame();
            } else if(action.get(0).equals(setPrivateCard)) {
                client.setPrivateCard(action);
            } else if(action.get(0).equals(setSchemas)) {
                client.setSchemas(action);
            } else if(action.get(0).equals(setPublicObjectives)) {
                client.setPublicObjectives(action);
            } else if(action.get(0).equals(setToolCards)) {
                client.setToolCards(action);
            }else if(action.get(0).equals(approvedSchema)){
                client.chooseSchema(action);
            }

        }catch(RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof RmiServerConnection) )
            return false;

        RmiServerConnection cli = (RmiServerConnection) obj;
       return (this.client.equals(cli.client)) ;
    }
}
