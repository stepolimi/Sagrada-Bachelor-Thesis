package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.model.game.Session;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.costants.LoginMessages.loginError;
import static it.polimi.ingsw.costants.LoginMessages.loginSuccessful;

public class VirtualView extends Observable implements Observer{
    private Connected connection;

    public void forwardAction(List action)
    {
        setChanged();
        notifyObservers(action);
    }


    public void update(Observable o, Object arg) {
        if (o.getClass() == Session.class) { sessionHandler(arg); }
        else if (o.getClass() == GameMultiplayer.class) { gameMultiplayerHandler(arg); }
        else if (o.getClass() == Board.class) { connection.forwardMessage((List)arg); }
        else if (o.getClass() == Round.class) { connection.sendMessage((List)arg); }
        else if (o.getClass() == Player.class) { connection.sendMessage((List)arg); }
        else if (o.getClass() == Schema.class) {connection.sendMessage((List)arg);}

    }

    private void sessionHandler(Object action) {
        if(((List) action).get(0).equals(loginError) )
            connection.sendMessage((List)action);
        else
            connection.forwardMessage((List)action);
    }

    private void gameMultiplayerHandler(Object action) {
        connection.sendMessage((List)action);
    }

    public void setConnection(Connected connection) {
        this.connection = connection;
    }



}
