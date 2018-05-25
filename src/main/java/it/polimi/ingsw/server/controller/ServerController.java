package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.Session;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.states.RoundManager;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ServerController implements Observer{
    private Session session;
    private VirtualView view;
    private GameMultiplayer game;
    private RoundManager roundManager;
    private Round round;

    public ServerController(Session session, VirtualView view) {
        this.session = session;
        this.view = view;
    }

    public void update(Observable os, Object action) {
        String head = (String) ((List)action).get(0);

        if(head.equals("Login")) {loginManager((List)action); }
        else if(head.equals("Disconnected")) { logoutManager((List)action); }
        else if(head.equals("ChooseSchema")) {chooseSchemaManager((List)action); }
        else if(head.equals("ExtractDice")) {extractDiceManager((List)action); }
        else if(head.equals("TakeDice")) { takeDiceManager((List)action); }
        else if(head.equals("PlaceDice")) {placeDiceManager((List)action); }
        else if(head.equals("UseCard")) {useCardManager((List)action); }
        else if(head.equals("EndTurn")) {endTurnManager((List)action); }

    }

    public void loginManager(List action){
        session.joinPlayer((String) action.get(1));

    }

    public void logoutManager(List action){
        session.removePlayer((String) action.get(1));

    }

    public void chooseSchemaManager(List action){
        game = session.getGame();
        roundManager = game.getRoundManager();
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(action.get(1))) {
                p.setSchema((String) action.get(2));
                if(p.getNameSchemas().contains(action.get(2))) {
                    p.getSchema().setRulesManager(game.getRulesManager());
                    game.getBoard().addSchema(p.getSchema());
                }
            }
        }
        if(game.getBoard().getDeckSchemas().size() == game.getBoard().getPlayerList().size()) {
            roundManager.setFirstPlayer();
            roundManager.startNewRound();
            round = roundManager.getRound();
        }
    }

    public void extractDiceManager(List action){
        round.execute(action);
    }

    public void takeDiceManager(List action){
        round.execute(action);
    }

    public void placeDiceManager(List action){
        round.execute(action);
    }



    public void useCardManager(List action){
        round.setUsingTool((Integer)action.get(1));




        round.setUsingTool(0);
    }

    public void endTurnManager(List action){
        round.execute(action);
        if(round.getTurnNumber() == game.getBoard().getPlayerList().size() -1){
            game.getBoard().getRoundTrack().insertDices(game.getBoard().getDiceSpace().getListDice(),roundManager.getRoundNumber());
            if(roundManager.getRoundNumber() <=10) {
                roundManager.startNewRound();
                round = roundManager.getRound();
            }
            else{
                game.endGame();
            }

        }
    }

}
