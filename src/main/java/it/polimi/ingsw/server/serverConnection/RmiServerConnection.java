package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.client.clientConnection.RmiClientMethodInterface;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameConstants.SET_DICE_SPACE;
import static it.polimi.ingsw.costants.GameCreationMessages.*;
import static it.polimi.ingsw.costants.LoginMessages.*;
import static it.polimi.ingsw.costants.TimerCostants.SCHEMAS_TIMER_PING;
import static it.polimi.ingsw.costants.TimerCostants.TURN_TIMER_PING;

public class RmiServerConnection implements Connection{
    RmiClientMethodInterface client;
    RmiServerMethod serverMethod;

    public RmiServerConnection(RmiClientMethodInterface client, RmiServerMethod serverMethod) {
        this.client = client;
        this.serverMethod = serverMethod;
    }

    public void sendMessage(List action) {
        try {

            if ((action.get(0)).equals(LOGIN_SUCCESSFUL)) {
                client.login(action);
            } else if ((action.get(0)).equals(LOGIN_ERROR)) {
                client.login(action);
            } else if (action.get(0).equals(LOGOUT)) {
                client.playerDisconnected(action);
            } else if (action.get(0).equals(TIMER_PING)) {
                client.timerPing(action);
            } else if (action.get(0).equals(SCHEMAS_TIMER_PING)) {
                //client.schemasTimerPing(action);
            } else if (action.get(0).equals(TURN_TIMER_PING)) {
                //client.turnTimerPing(action);
            } else if (action.get(0).equals(STARTING_GAME_MSG)) {
                client.createGame();
            } else if (action.get(0).equals(SET_PRIVATE_CARD)) {
                String privateCard = (String) action.get(1);
                client.setPrivateCard(privateCard);
            } else if (action.get(0).equals(SET_SCHEMAS)) {
                client.setSchemas(action);
            } else if (action.get(0).equals(SET_PUBLIC_OBJECTIVES)) {
                client.setPublicObjectives(action);
            } else if (action.get(0).equals(SET_TOOL_CARDS)) {
                client.setToolCards(action);
            } else if (action.get(0).equals(APPROVED_SCHEMA)) {
                client.chooseSchema(action);
            } else if (action.get(0).equals(SET_OPPONENTS_SCHEMAS)) {
                client.setOpponentsSchemas(action);
            } else if (action.get(0).equals(START_TURN)) {
                client.startTurn(action);
            } else if (action.get(0).equals(START_ROUND)) {
                client.startRound();
            } else if (action.get(0).equals(SET_ACTIONS)) {
                client.setActions(action);
            } else if (action.get(0).equals(SET_DICE_SPACE)) {
                client.setDiceSpace(action);
            } else if (action.get(0).equals(PLACE_DICE_SPACE)) {
                client.placeDiceSpace(action);
            } else if (action.get(0).equals(PICK_DICE_SPACE)) {
                client.pickDiceSpace(action);
            } else if (action.get(0).equals(PICK_DICE_SPACE_ERROR)) {
                client.pickDiceSpaceError();
            } else if (action.get(0).equals(PICK_DICE_SCHEMA)) {
                client.pickDiceSchema(action);
            } else if (action.get(0).equals(PICK_DICE_SCHEMA_ERROR)) {
                client.pickDiceSchemaError();
            } else if (action.get(0).equals(PLACE_DICE_SCHEMA)) {
                client.placeDiceSchema(action);
            } else if (action.get(0).equals(PLACE_DICE_SCHEMA_ERROR)) {
                client.placeDiceSchemaError();
            } else if (action.get(0).equals(PLACE_DICE_ROUND_TRACK)) {
                client.placeDiceRoundTrack(action);
            } else if (action.get(0).equals(PICK_DICE_ROUND_TRACK)) {
                client.pickDiceRoundTrack(action);
            } else if (action.get(0).equals(PICK_DICE_ROUND_TRACK_ERROR)) {
                client.pickDiceRoundTrackError();
            } else if (action.get(0).equals(INSERT_DICE_ACCEPTED)) {
                client.insertDiceAccepted();
            } else if (action.get(0).equals(DRAFT_DICE_ACCEPTED)) {
                client.draftDiceAccepted();
            } else if (action.get(0).equals(MOVE_DICE_ACCEPTED)) {
                client.moveDiceAccepted();
            } else if (action.get(0).equals(SWAP_DICE_ACCEPTED)) {
                client.swapDiceAccepted();
            } else if (action.get(0).equals(USE_TOOL_CARD_ACCEPTED)) {
                client.useToolCardAccepted(action);
            } else if (action.get(0).equals(USE_TOOL_CARD_ERROR)) {
                client.useToolCardError();
            } else if (action.get(0).equals(CHANGE_VALUE_ACCEPTED)) {
                client.changeValueAccepted();
            } else if (action.get(0).equals(CHANGE_VALUE_ERROR)) {
                client.changeValueError();
            } else if (action.get(0).equals(PLACE_DICE_ACCEPTED)) {
                client.placeDiceAccepted();
            } else if (action.get(0).equals(ROLL_DICE_ACCEPTED)) {
                client.rollDiceAccepted(action);
            } else if (action.get(0).equals(CANCEL_USE_TOOL_CARD_ACCEPTED)) {
                client.cancelUseToolCardAccepted(action);
            } else if(action.get(0).equals(FLIP_DICE_ACCEPTED)) {
                client.flipDiceAccepted(action);
            }else if(action.get(0).equals(PLACE_DICE_SPACE_ACCEPTED)) {
                client.placeDiceSpaceAccepted();
            }else if(action.get(0).equals(ROLL_DICE_SPACE_ACCEPTED)) {
                client.rollDiceSpaceAccepted(action);
            }else if(action.get(0).equals(SWAP_DICE_BAG_ACCEPTED)) {
                client.swapDiceBagAccepted(action);
            }else if(action.get(0).equals(CHOOSE_VALUE_ACCEPTED)) {
                client.chooseValueAccepted();
            }else if(action.get(0).equals(CHOOSE_VALUE_ERROR)) {
                client.chooseValueError();
            }else if(action.get(0).equals(APPROVED_SCHEMA_CUSTOM)) {
                client.schemaCustomAccepted(action);  //invio il nome dello schema
            }else if(action.get(0).equals(SET_OPPONENTS_CUSTOM_SCHEMAS)) {
                client.setOpponentsCustomSchemas(action);    //player-json
            }


        }catch(RemoteException e) {
            serverMethod.disconnected(this.client);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj.getClass().equals(RmiServerConnection.class)) )
            return false;

        RmiServerConnection cli = (RmiServerConnection) obj;
       return (this.client.equals(cli.client)) ;
    }
}
