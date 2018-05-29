package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.client.clientConnection.RmiClientMethodInterface;
import it.polimi.ingsw.costants.GameConstants;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameConstants.setDiceSpace;
import static it.polimi.ingsw.costants.GameCreationMessages.*;
import static it.polimi.ingsw.costants.LoginMessages.*;
import static it.polimi.ingsw.costants.TimerCostants.schemasTimerPing;
import static it.polimi.ingsw.costants.TimerCostants.turnTimerPing;

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
            } else if((action.get(0)).equals(loginError) ){
                client.login(action);
            } else if(action.get(0).equals(logout)){
                client.playerDisconnected(action);
            } else if(action.get(0).equals(timerPing)) {
                client.timerPing(action);
            } else if(action.get(0).equals(schemasTimerPing)) {
                //client.schemasTimerPing(action);
            } else if(action.get(0).equals(turnTimerPing)) {
                //client.turnTimerPing(action);
            } else if(action.get(0).equals(startingGameMsg)) {
                client.createGame();
            } else if(action.get(0).equals(setPrivateCard)) {
                String privateCard = (String)action.get(1);
                client.setPrivateCard(privateCard);
            } else if(action.get(0).equals(setSchemas)) {
                client.setSchemas(action);
            } else if(action.get(0).equals(setPublicObjectives)) {
                client.setPublicObjectives(action);
            } else if(action.get(0).equals(setToolCards)) {
                client.setToolCards(action);
            } else if(action.get(0).equals(approvedSchema)){
                client.chooseSchema(action);
            } else if(action.get(0).equals(setOpponentsSchemas)){
               client.setOpponentsSchemas(action);
            } else if(action.get(0).equals(startTurn)) {
                client.startTurn(action);
            } else if(action.get(0).equals(startRound)) {
                client.startRound(action);
            } else if(action.get(0).equals(setActions)) {
                client.setActions(action);
            } else if(action.get(0).equals(insertDiceAccepted)) {
                client.insertDiceAccepted(action);
            } else if(action.get(0).equals(setDiceSpace)) {
                client.setDiceSpace(action);
            } else if(action.get(0).equals(placeDiceSpace)) {
                //client.placeDiceSpace(action);
            }else if(action.get(0).equals(placeDiceSpaceError)) {
                //client.placeDiceSpaceError(action);
            } else if(action.get(0).equals(pickDiceSpace)) {
                client.pickDiceSpace(action);
            }else if(action.get(0).equals(pickDiceSpaceError)) {
                client.pickDiceSpaceError(action);
            } else if(action.get(0).equals(pickDiceSchema)) {
                //client.pickDiceSchema(action);
            } else if(action.get(0).equals(pickDiceSchemaError)) {
                //client.pickDiceSchemaError(action);
            } else if(action.get(0).equals(placeDiceSchema)) {
                client.placeDiceSchema(action);
            } else if(action.get(0).equals(placeDiceSchemaError)) {
                client.placeDiceSchemaError(action);
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
