package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.DiceSpace;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.model.game.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameCreationMessages.START_TURN;
import static it.polimi.ingsw.costants.LoginMessages.LOGIN_ERROR;

public class VirtualView extends Observable implements Observer{
    private Connected connection;

    public void forwardAction(List action)
    {
        setChanged();
        notifyObservers(action);
    }


    public void update(Observable o, Object arg) {
        if (o.getClass() == Session.class) { sessionHandler(arg); }
        else if (o.getClass() == GameMultiplayer.class) { connection.sendMessage((List)arg); }
        else if (o.getClass() == Board.class) { connection.forwardMessage((List)arg); }
        else if (o.getClass() == Round.class) { roundHandler(arg); }
        else if (o.getClass() == Player.class) { connection.sendMessage((List)arg); }
        else if (o.getClass() == Schema.class) {schemaHandler(arg);}
        else if (o.getClass() == DiceSpace.class) {diceSpaceHandler(arg);}
    }

    public void sendError(String player){
        List action = new ArrayList();
        action.add("instructionError");
        action.add(player);
        connection.sendMessage(action);
    }

    private void sessionHandler(Object action) {
        if(((List) action).get(0).equals(LOGIN_ERROR) )
            connection.sendMessage((List)action);
        else
            connection.forwardMessage((List)action);
    }

    private void roundHandler(Object action) {
        if(((List) action).get(0).equals(START_TURN) || ((List) action).get(0).equals(START_ROUND) )
            connection.forwardMessage((List)action);
        else
            connection.sendMessage((List)action);
    }

    private void schemaHandler(Object action) {
        if(((List) action).get(0).equals(PLACE_DICE_SCHEMA_ERROR) ||((List) action).get(0).equals(PICK_DICE_SCHEMA_ERROR)  )
            connection.sendMessage((List)action);
        else
            connection.forwardMessage((List)action);
    }

    private void diceSpaceHandler(Object action) {
        if(((List) action).get(0).equals(PICK_DICE_SPACE_ERROR) )
            connection.sendMessage((List)action);
        else
            connection.forwardMessage((List)action);
    }

    public void setConnection(Connected connection) {
        this.connection = connection;
    }



}
