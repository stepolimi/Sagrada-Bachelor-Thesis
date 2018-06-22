package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.server.serverConnection.Connected;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameCreationMessages.*;
import static it.polimi.ingsw.costants.GameCreationMessages.APPROVED_SCHEMA_CUSTOM;
import static it.polimi.ingsw.costants.GameCreationMessages.SET_OPPONENTS_CUSTOM_SCHEMAS;
import static it.polimi.ingsw.costants.LoginMessages.*;
import static it.polimi.ingsw.costants.LoginMessages.STARTING_GAME_MSG;

public class VirtualView extends Observable implements Observer {
    private Connected connection;

    public void forwardAction(List action) {
        setChanged();
        notifyObservers(action);
    }


    public void update(Observable o, Object arg) {
        String command = ((String)((List)arg).get(0));
        String nickname;
        List action = (List)arg;
        List<String> colours;
        List<Integer> values;

        switch (command){
            case LOGIN_SUCCESSFUL:
                connection.login((String)action.get(1),(Integer)action.get(2));
                break;
            case LOGIN_ERROR:
                connection.loginError((String)action.get(1),(String)action.get(2));
                break;
            case LOGOUT:
                connection.playerDisconnected((String)action.get(1));
                break;
            case TIMER_PING:
                connection.timerPing((Integer)action.get(1));
                break;
            case STARTING_GAME_MSG:
                connection.createGame();
                break;
            case SET_SCHEMAS:
                action.remove(0);
                nickname = (String)action.get(0);
                action.remove(0);
                connection.setSchemas(nickname,action);
                break;
            case SET_PRIVATE_CARD:
                connection.setPrivateCard((String)action.get(1),(String)action.get(2));
                break;
            case SET_PUBLIC_OBJECTIVES:
                action.remove(0);
                connection.setPublicObjectives(action);
                break;
            case SET_TOOL_CARDS:
                action.remove(0);
                connection.setToolCards(action);
                break;
            case APPROVED_SCHEMA:
                connection.chooseSchema((String)action.get(1),(String)action.get(2));
                break;
            case SET_OPPONENTS_SCHEMAS:
                action.remove(0);
                connection.setOpponentsSchemas(action);
                break;
            case APPROVED_SCHEMA_CUSTOM:
                connection.schemaCustomAccepted((String)action.get(1),(String)action.get(2));
                break;
            case SET_OPPONENTS_CUSTOM_SCHEMAS:
                action.remove(0);
                connection.setOpponentsCustomSchemas(action);
                break;
            case START_ROUND:
                connection.startRound();
                break;
            case START_TURN:
                connection.startTurn((String)action.get(1));
                break;
            case SET_ACTIONS:
                action.remove(0);
                nickname = (String)action.get(0);
                action.remove(0);
                connection.setActions(nickname,action);
                break;
            case SET_DICE_SPACE:
                colours = new ArrayList<>();
                values = new ArrayList<>();
                for(int i=1; i<action.size(); i+=2){
                    colours.add((String) action.get(i));
                    values.add((Integer)action.get(i+1));
                }
                connection.setDiceSpace(colours,values);
                break;
            case DRAFT_DICE_ACCEPTED:
                connection.draftDiceAccepted((String)action.get(1));
                break;
            case INSERT_DICE_ACCEPTED:
                connection.insertDiceAccepted((String)action.get(1));
                break;
            case MOVE_DICE_ACCEPTED:
                connection.moveDiceAccepted((String)action.get(1));
                break;
            case PICK_DICE_SPACE:
                connection.pickDiceSpace((Integer)action.get(1));
                break;
            case PICK_DICE_SPACE_ERROR:
                connection.pickDiceSpaceError((String)action.get(1));
                break;
            case PLACE_DICE_SCHEMA:
                connection.placeDiceSchema((String)action.get(1),(Integer)action.get(2),(Integer)action.get(3),(String)action.get(4),(Integer)action.get(5));
                break;
            case PLACE_DICE_SCHEMA_ERROR:
                connection.placeDiceSchemaError((String)action.get(1));
                break;
            case PICK_DICE_SCHEMA:
                connection.pickDiceSchema((String)action.get(1),(Integer)action.get(2),(Integer)action.get(3));
                break;
            case PICK_DICE_SCHEMA_ERROR:
                connection.pickDiceSchemaError((String)action.get(1));
                break;
            case USE_TOOL_CARD_ACCEPTED:
                connection.useToolCardAccepted((String)action.get(1),(Integer)action.get(2));
                break;
            case USE_TOOL_CARD_ERROR:
                connection.useToolCardError((String)action.get(1));
                break;
            case CHANGE_VALUE_ACCEPTED:
                connection.changeValueAccepted((String)action.get(1));
                break;
            case CHANGE_VALUE_ERROR:
                connection.changeValueError((String)action.get(1));
                break;
            case PLACE_DICE_ACCEPTED:
                connection.placeDiceAccepted((String)action.get(1));
                break;
            case ROLL_DICE_ACCEPTED:
                connection.rollDiceAccepted((String)action.get(1),(Integer)action.get(2));
                break;
            case SWAP_DICE_ACCEPTED:
                connection.swapDiceAccepted((String)action.get(1));
                break;
            case PICK_DICE_ROUND_TRACK:
                connection.pickDiceRoundTrack((Integer)action.get(1),(Integer)action.get(2));
                break;
            case PICK_DICE_ROUND_TRACK_ERROR:
                connection.pickDiceRoundTrackError((String)action.get(1));
                break;
            case PLACE_DICE_ROUND_TRACK:
                colours = new ArrayList<>();
                values = new ArrayList<>();
                for(int i=2; i<action.size(); i+=2){
                    colours.add((String) action.get(i));
                    values.add((Integer)action.get(i+1));
                }
                connection.placeDiceRoundTrack((Integer)action.get(1),colours,values);
                break;
            case FLIP_DICE_ACCEPTED:
                connection.flipDiceAccepted((String)action.get(1),(Integer)action.get(2));
                break;
            case CANCEL_USE_TOOL_CARD_ACCEPTED:
                connection.cancelUseToolCardAccepted((String)action.get(1),(Integer)action.get(2));
                break;
            case PLACE_DICE_DICESPACE:
                connection.placeDiceSpace((String)action.get(1),(Integer)action.get(2));
                break;
            case PLACE_DICE_SPACE_ACCEPTED:
                connection.placeDiceSpaceAccepted((String)action.get(1));
                break;
            case ROLL_DICE_SPACE_ACCEPTED:
                connection.rollDiceSpaceAccepted((String)action.get(1));
                break;
            case SWAP_DICE_BAG_ACCEPTED:
                connection.swapDiceBagAccepted((String)action.get(1),(String)action.get(2),(Integer)action.get(3));
                break;
            case CHOOSE_VALUE_ACCEPTED:
                connection.chooseValueAccepted((String)action.get(1));
                break;
            case CHOOSE_VALUE_ERROR:
                connection.chooseValueError((String)action.get(1));
                break;
            default:
                break;
        }
    }

    public void sendError(String player) {
        System.out.println("message error by " + player);
    }

    public void setConnection(Connected connection) {
        this.connection = connection;
    }



}
