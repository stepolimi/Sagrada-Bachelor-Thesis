package it.polimi.ingsw.Server.Controller;

import exception.GameRunningException;
import exception.NotValidNicknameException;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.board.Schema;
import it.polimi.ingsw.Server.Model.game.GameMultiplayer;
import it.polimi.ingsw.Server.Model.game.Session;
import it.polimi.ingsw.Server.VirtualView.VirtualView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ServerController implements Observer{
    private Session session;
    private VirtualView view;
    private GameMultiplayer game;

    public ServerController(Session session, VirtualView view) {
        this.session = session;
        this.view = view;
    }

    public void update(Observable os, Object action) {
        String head = (String) ((List)action).get(0);

        if(head.equals("Login")) {loginManager((List)action); }
        else if(head.equals("Logout")) { logoutManager((List)action); }
        else if(head.equals("ChooseSchema")) {chooseSchemaManager((List)action); }
        else if(head.equals("TakeDice")) { takeDiceManager((List)action); }
        else if(head.equals("PlaceDice")) {placeDiceManager((List)action); }
        else if(head.equals("ExtractDice")) {extractDiceManager((List)action); }
        else if(head.equals("UseCard")) {useCardManager((List)action); }
        else if(head.equals("EndTurn")) {endTurnManager((List)action); }

    }

    public void loginManager(List action){
        try {
            session.joinPlayer((String) action.get(1));
        }
        catch(GameRunningException e){
            System.out.println("Partita in corso");
        }
        catch(NotValidNicknameException e){
            System.out.println("Nickname non valido");
        }
    }

    public void logoutManager(List action){
        try {
            session.removePlayer((String) action.get(1));
        }
        catch(GameRunningException e){
            System.out.println("Partita in corso");
        }

    }

    public void chooseSchemaManager(List action){
        List<Schema> schemas;
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(action.get(1))) {
                schemas = game.getBoard().getDeckSchemas().deliver(game.getBoard().getIndex(p));
                p.takeSchema(schemas, (Integer)action.get(2));
                game.getBoard().addSchema(schemas.get((Integer)action.get(2)));
            }
        }

    }

    public void takeDiceManager(List action){

    }

    public void placeDiceManager(List action){

    }

    public void extractDiceManager(List action){

    }

    public void useCardManager(List action){

    }

    public void endTurnManager(List action){

    }

}
