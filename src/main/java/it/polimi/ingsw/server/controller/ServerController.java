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

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameCreationMessages.END_TURN;
import static it.polimi.ingsw.costants.LoginMessages.DISCONNECTED;
import static it.polimi.ingsw.costants.LoginMessages.LOGIN;
import static it.polimi.ingsw.server.builders.SchemaBuilder.buildSchema;

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

        switch (head) {
            case LOGIN:
                loginManager((List) action);
                break;
            case DISCONNECTED:
                logoutManager((List) action);
                break;
            case CHOOSE_SCHEMA:
                chooseSchemaManager((List) action);
                break;
            case CUSTOM_SCHEMA:
                customSchemaManager((List) action);
                break;
            case END_TURN:
                endTurnManager((List) action);
                break;
            default:
                if(session.getGame()!= null)
                    changeStateManager((List) action);
                else
                    view.sendError((String)((List)action).get(1));
                break;
        }
    }

    private void loginManager(List action){
        session.joinPlayer((String) action.get(1));

    }

    private void logoutManager(List action){
        session.removePlayer((String) action.get(1));

    }

    private void chooseSchemaManager(List action){
        game = session.getGame();
        roundManager = game.getRoundManager();
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(action.get(1)) && p.getNameSchemas().contains(action.get(2))) {
                    p.setSchema((String) action.get(2));
                    game.getBoard().addDefaultSchema(p.getSchema());
            }
        }
        if(game.getBoard().getDeckSchemas().size() == game.getBoard().getPlayerList().size()) {
            startGame();
        }
    }

    private void customSchemaManager(List action){
        game = session.getGame();
        roundManager = game.getRoundManager();
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(action.get(1))) {
                p.setCustomSchema(buildSchema((String)action.get(2)));
                game.getBoard().addCustomSchema(p.getSchema());
            }
        }
        if(game.getBoard().getDeckSchemas().size() == game.getBoard().getPlayerList().size()) {
            startGame();
        }
    }

    private void startGame(){
        game.getTimer().cancel();
        roundManager.setFirstPlayer();
        roundManager.startNewRound();
        round = roundManager.getRound();
    }

    private void changeStateManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }

    private void endTurnManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        if(round.getTurnNumber() == game.getBoard().getPlayerList().size()*2 -1){
            round.getTimer().cancel();
            if(roundManager.getRoundNumber() <10) {
                game.getBoard().getRoundTrack().insertDices(game.getBoard().getDiceSpace().getListDice(),roundManager.getRoundNumber() - 1);
                roundManager.startNewRound();
                round = roundManager.getRound();
            }
            else{
                game.endGame();
            }
        }
        else
            round.execute(action);
    }

}
