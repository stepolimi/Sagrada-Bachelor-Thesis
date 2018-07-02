package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.Session;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.RoundManager;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.server.costants.MessageConstants.*;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;

public class ServerController implements Observer{
    private static ServerController instance = null;
    private Session session;
    private VirtualView view;
    private GameMultiplayer game;
    private RoundManager roundManager;
    private Round round;

    private ServerController() {
        this.session = Session.getSession();
        this.view = VirtualView.getVirtualView();
    }

    public static synchronized ServerController getServerController(){
        if(instance == null){
            instance = new ServerController();
        }
        return instance;
    }

    public void update(Observable os, Object action) {
        Message message = (Message)action;

        switch (message.getHead()) {
            case LOGIN:
                loginManager(message);
                break;
            case DISCONNECTED:
                logoutManager(message);
                break;
            case CHOOSE_SCHEMA:
                chooseSchemaManager(message);
                break;
            case CUSTOM_SCHEMA:
                customSchemaManager(message);
                break;
            case END_TURN:
                endTurnManager(message);
                break;
            default:
                if(session.getGame()!= null)
                    changeStateManager(message);
                else
                    view.sendError();
                break;
        }
    }

    private void loginManager(Message message){
        session.joinPlayer(message.getPlayers().get(0));
    }

    private void logoutManager(Message message){
        session.removePlayer(message.getPlayers().get(0));
    }

    private void chooseSchemaManager(Message message){
        game = session.getGame();
        roundManager = game.getRoundManager();
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(message.getPlayers().get(0)) && p.getNameSchemas().contains(message.getStringArgument(0))) {
                    p.setSchema(message.getStringArgument(0));
                    game.getBoard().addDefaultSchema(p.getSchema());
            }
        }
        if(game.getBoard().getDeckSchemas().size() == game.getBoard().getPlayerList().size()) {
            startGame();
        }
    }

    private void customSchemaManager(Message message){
        game = session.getGame();
        roundManager = game.getRoundManager();
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(message.getPlayers().get(0))) {
                p.setCustomSchema(buildSchema(message.getStringArgument(0)));
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

    private void changeStateManager(Message message){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(message);
    }

    private void endTurnManager(Message message){
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
                game.endGame(round.getCurrentPlayer());
            }
        }
        else
            round.execute(message);
    }

}
