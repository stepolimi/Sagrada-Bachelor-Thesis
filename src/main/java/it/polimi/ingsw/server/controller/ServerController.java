package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.Session;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.RoundManager;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.server.costants.MessageConstants.*;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;

public class ServerController implements Observer{
    private static ServerController instance = null;
    private Session session;

    private ServerController() {
        this.session = Session.getSession();
    }

    public static synchronized ServerController getServerController(){
        if(instance == null){
            instance = new ServerController();
        }
        return instance;
    }

    /**
     * Identifies the type of the message and manages it appropriately.
     * @param os is the the Observable object.
     * @param action is the update sent by a player.
     */
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
                changeStateManager(message);
                break;
        }
    }

    /**
     * Removes all the players of the specified game from the players in an active game.
     * @param game is a game ended to be eliminated.
     */
    private void deleteGame(GameMultiplayer game){
        session.getPlayersInGames().forEach((player,gameMultiPlayer) -> {
            if(gameMultiPlayer == game)
                session.getPlayersInGames().remove(player);
        });
    }

    /**
     * Manages the login of a new player or the reconnection of one who is already in a game.
     * @param message is the update sent by a player. Contains an identifier and the name of the player.
     */
    private synchronized void loginManager(Message message){
        String player = message.getPlayers().get(0);
        GameMultiplayer game = session.getPlayersInGames().get(player);
        if(game == null) {
            session.joinPlayer(message.getPlayers().get(0));
            return;
        }
        if(!game.isEnded()) {
            session.reconnectPlayer(player, game);
            return;
        }
        deleteGame(game);
        session.joinPlayer(player);
    }

    /**
     * Manages the logout of a player that is in the lobby or already in a game.
     * @param message is the update sent by a player. Contains an identifier and the name of the player.
     */
    private synchronized void logoutManager(Message message){
        String player = message.getPlayers().get(0);
        GameMultiplayer game = session.getPlayersInGames().get(player);
        if(game == null) {
            if(session.getNicknames().contains(player))
                session.removePlayer(message.getPlayers().get(0));
            return;
        }
        if(!game.isEnded()){
            session.disconnectPlayer(player,game);
            return;
        }
        deleteGame(game);
    }

    /**
     * manages the schema's choice of a player.
     * @param message is the update sent by a player.
     *                Contains an identifier, the name of the player and the name of the chosen schema.
     */
    private void chooseSchemaManager(Message message){
        String player = message.getPlayers().get(0);
        GameMultiplayer game = session.getPlayersInGames().get(player);
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(message.getPlayers().get(0)) && p.getNameSchemas().contains(message.getStringArgument(0))) {
                    p.setSchema(message.getStringArgument(0));
                    game.getBoard().addDefaultSchema(p.getSchema());
            }
        }
        if(game.getBoard().getDeckSchemas().size() == game.getBoard().getPlayerList().size())
            startGame(game);
    }

    /**
     * Manages the custom schema's choice of a player.
     * @param message is the update sent by a player. Contains an identifier, the name of the player and the chosen schema.
     */
    private void customSchemaManager(Message message){
        String player = message.getPlayers().get(0);
        GameMultiplayer game = session.getPlayersInGames().get(player);
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(message.getPlayers().get(0))) {
                p.setCustomSchema(buildSchema(message.getStringArgument(0)));
                game.getBoard().addCustomSchema(p.getSchema());
            }
        }
        if(game.getBoard().getDeckSchemas().size() == game.getBoard().getPlayerList().size())
            startGame(game);
    }

    /**
     * Starts a new game.
     * @param game is the game to start.
     */
    private void startGame(GameMultiplayer game){
        game.getTimer().cancel();
        game.getRoundManager().setFirstPlayer();
        game.getRoundManager().startNewRound();
    }

    /**
     * Manages all the in-game actions of a player except for the end of a turn.
     * @param message is the update sent by a player.
     */
    private void changeStateManager(Message message){
        String player = message.getPlayers().get(0);
        GameMultiplayer game = session.getPlayersInGames().get(player);
        game.getRoundManager().getRound().execute(message);
    }

    /**
     * Manages the end of a turn. Can start a new turn, a new round or end the game.
     * @param message is the update sent by a player. Contains an identifier and the name of the player.
     */
    private void endTurnManager(Message message){
        String player = message.getPlayers().get(0);
        GameMultiplayer game = session.getPlayersInGames().get(player);
        RoundManager roundManager = game.getRoundManager();
        Round round = roundManager.getRound();

        if(round.getTurnNumber() == game.getBoard().getPlayerList().size()*2 -1){
            round.getTimer().cancel();
            if(roundManager.getRoundNumber() <10) {
                game.getBoard().getRoundTrack().insertDices(game.getBoard().getDiceSpace().getListDice(),roundManager.getRoundNumber() - 1);
                roundManager.startNewRound();
            }
            else
                game.endGame(round.getCurrentPlayer());
        }
        else
            round.execute(message);
    }

}
