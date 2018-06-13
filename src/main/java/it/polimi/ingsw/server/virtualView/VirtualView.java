package it.polimi.ingsw.server.virtualView;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.board.*;
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
    private static final int ROWS = 4;
    private static final int COLUMNS = 5;
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
        else if (o.getClass() == RoundTrack.class) {roundTrackHandler(arg);}
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

    private void roundTrackHandler(Object action) {
        if(((List) action).get(0).equals(PICK_DICE_ROUND_TRACK_ERROR) || ((List) action).get(0).equals(PLACE_DICE_ROUND_TRACK_ERROR))
            connection.sendMessage((List)action);
        else
            connection.forwardMessage((List)action);
    }

    public void setConnection(Connected connection) {
        this.connection = connection;
    }

/*public Schema parserSchema(String schema)
{
    Gson g = new Gson();
    Schema schemaServer = new Schema();
    it.polimi.ingsw.client.view.Schema schemaClient;
    schemaClient = g.fromJson(schema, it.polimi.ingsw.client.view.Schema.class);
    int nConstraint = 20;
    for(int i =0;i<ROWS;i++)
    {
        for(int j=0;j<COLUMNS;j++)
        {
            String constraint = schemaClient.getGrid()[i][j].getConstraint();

            if(constraint.equals(Colour.ANSI_RED.escape()))
            {
                schemaServer.setTable(i,j,new Box(Colour.ANSI_RED,0));
                continue;
            } else if(constraint.equals(Colour.ANSI_GREEN.escape()))
            {
                schemaServer.setTable(i,j,new Box(Colour.ANSI_GREEN,0));
                continue;
            }else if(constraint.equals(Colour.ANSI_BLUE.escape()))
            {
                schemaServer.setTable(i,j,new Box(Colour.ANSI_BLUE,0));
                continue;
            } else if(constraint.equals(Colour.ANSI_PURPLE.escape()))
            {
                schemaServer.setTable(i,j,new Box(Colour.ANSI_PURPLE,0));
                continue;
            } else if(constraint.equals(Colour.ANSI_YELLOW.escape()))
            {
                schemaServer.setTable(i,j,new Box(Colour.ANSI_YELLOW,0));
                continue;
            }else if(constraint.equals(""))
            {
                schemaServer.setTable(i,j,new Box(null,0));
                nConstraint--;
                continue;
            }

            switch (constraint.charAt(0))
            {
                case '1': schemaServer.setTable(i,j,new Box(null,1)); break;
                case '2':schemaServer.setTable(i,j,new Box(null,2)); break;
                case '3':schemaServer.setTable(i,j,new Box(null,3)); break;
                case '4':schemaServer.setTable(i,j,new Box(null,4)); break;
                case '5':schemaServer.setTable(i,j,new Box(null,5)); break;
                case '6':schemaServer.setTable(i,j,new Box(null,6)); break;
            }

        }
    }
    // ricalcolo la difficoltà
    schemaClient.setDifficult(nConstraint);
    schemaServer.setDifficult(schemaClient.getDifficult());

    schemaServer.setName(schemaClient.getName());

    return schemaServer;
}

*/
}
