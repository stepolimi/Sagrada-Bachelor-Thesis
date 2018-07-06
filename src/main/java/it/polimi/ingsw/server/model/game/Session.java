package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.timer.GameTimer;
import it.polimi.ingsw.server.model.timer.TimedComponent;
import it.polimi.ingsw.server.set.up.TakeDataFile;
import it.polimi.ingsw.server.virtual.view.VirtualView;

import java.util.*;
import java.util.logging.Level;


import static it.polimi.ingsw.server.costants.Constants.EVERYONE;
import static it.polimi.ingsw.server.costants.Constants.MAX_PLAYERS;
import static it.polimi.ingsw.server.costants.Constants.MIN_PLAYERS;
import static it.polimi.ingsw.server.costants.LogConstants.*;
import static it.polimi.ingsw.server.costants.MessageConstants.*;
import static it.polimi.ingsw.server.costants.NameConstants.LOBBY_TIMER;
import static it.polimi.ingsw.server.costants.SetupConstants.CONFIGURATION_FILE;

/**
 * Manages the process of game start; players can join/left the lobby before game starts.
 * Players that will leave the game after it's start will be set as "disconnected" but not removed from the game.
 */
public  class Session extends Observable implements TimedComponent {
    private static Session instance = null;
    private List<Player> lobby ;
    private final Map<String,GameMultiPlayer> playersInGames;
    private Timer timer;
    private Long startingTime = 0L;
    private final int lobbyTimerValue;

    private Session() {
        TakeDataFile timerConfig;
        playersInGames = new HashMap<>();
        timerConfig = new TakeDataFile();
        lobbyTimerValue = Integer.parseInt(timerConfig.getParameter(LOBBY_TIMER));
        lobby = new ArrayList<>();
    }

    public static synchronized Session getSession(){
        if(instance == null)
            instance = new Session();
        return instance;
    }
    /**
     * Adds players in the game's lobby until it starts.
     * If the player is the second one, the timer get started.
     * When the lobby is full or the timer elapse, a new game is created.
     *
     * @param player name of the player that is going to join the lobby
     */
    public void joinPlayer(String player) {
        lobby.add(new Player(player));
        notifyChanges(LOGIN_SUCCESSFUL, player);
        Log.getLogger().addLog(CONNECT + lobby.size(), Level.INFO, this.getClass().getName(), SESSION_JOIN_PLAYER);
        if (lobby.size() == MIN_PLAYERS) {
            startingTime = System.currentTimeMillis();
            GameTimer lobbyTimer = new GameTimer(lobbyTimerValue, this);
            timer = new Timer();
            timer.schedule(lobbyTimer, 0L, 5000L);
        } else if (lobby.size() == MAX_PLAYERS) {
            timer.cancel();
            notifyChanges(START_GAME, EVERYONE);
            startGame();
        }
    }

    public void reconnectPlayer(String player, GameMultiPlayer game) {
        notifyChanges(RECONNECT_PLAYER, player);
        game.reconnectPlayer(player);
    }

    /**
     * Removes players from the lobby, if it's size reaches one player, the timer will be reset
     * @param player name of the player that is going to leave the lobby
     */
    public void removePlayer(String player) {
        for (int i = 0; i < lobby.size(); i++)
            if (lobby.get(i).getNickname().equals(player)) {
                notifyChanges(LOGOUT, player);
                lobby.remove(i);
            }
        if (lobby.size() == MIN_PLAYERS - 1) {
            timer.cancel();
            startingTime = 0L;
        }
        Log.getLogger().addLog(player + DISCONNECT + lobby.size(), Level.INFO, this.getClass().getName(), SESSION_REMOVE_PLAYER);

    }

    public void disconnectPlayer(String player, GameMultiPlayer game) {
        if (game.getRoundManager().getRound() != null && game.getRoundManager().getRound().getCurrentPlayer().getNickname().equals(player))
            game.getRoundManager().getRound().disconnectPlayer();
        else {
            for (Player p : game.getPlayers()) {
                if (p.getNickname().equals(player)) {
                    p.setConnected(false);
                    Log.getLogger().addLog(player + DISCONNECT + game.getBoard().getConnected(), Level.INFO, this.getClass().getName(), SESSION_DISCONNECT_PLAYER);
                    notifyChanges(LOGOUT, player);
                }
            }
            if (game.getBoard().getConnected() == 1) {
                if(game.getRoundManager().getRound()!= null)
                    game.endGame(game.getRoundManager().getRound().getCurrentPlayer());
                else
                    game.endGame(game.getPlayers().get(0));
            }
        }
    }

    public List<Player> getPlayersInLobby(){ return this.lobby; }

    public List<String> getNicknames(){
        List<String> nicknames = new ArrayList<>();
        lobby.forEach(player -> nicknames.add(player.getNickname()));
        return nicknames;
    }

    public Map<String,GameMultiPlayer> getPlayersInGames() { return playersInGames; }

    /**
     * Creates and initializes a new game and sets it's observer
     */
    private void startGame() {
        GameMultiPlayer game = new GameMultiPlayer(lobby);
        Log.getLogger().addLog(STARTING_GAME, Level.INFO, this.getClass().getName(), SESSION_START_GAME);
        game.addObserver(VirtualView.getVirtualView());
        game.gameInit();
        lobby.forEach(player -> playersInGames.put(player.getNickname(),game));
        lobby = new ArrayList<>();
        Log.getLogger().addLog(GAME_STARTED, Level.INFO, this.getClass().getName(), SESSION_START_GAME);
    }

    /**
     * Notifies that the timer is elapsed to the observer and makes the game start
     */
    public void timerElapsed() {
        notifyChanges(START_GAME,EVERYONE);
        startGame();
    }

    /**
     * Notifies the ping of the timer to the players.
     */
    public void timerPing(){
        notifyChanges(TIMER_PING,EVERYONE);
    }

    /**
     * Notifies different changes to the observer
     * @param string head of the message that will be sent to the observer
     * @param player name of the player to whom the message will be sent. Can be sent to all of them.
     */
    private void notifyChanges(String string, String player){
        Message message = new Message(string);

        switch (string) {
            case START_GAME:
                message.setPlayers(getNicknames());
                break;
            case TIMER_PING:
                message.addIntegerArgument((int) (lobbyTimerValue - (System.currentTimeMillis() - startingTime) / 1000));
                message.setPlayers(getNicknames());
                break;
            case RECONNECT_PLAYER:
                message.addPlayer(player);
                break;
            case LOGOUT:
                message.addStringArguments(player);
                if(getNicknames().contains(player))
                    message.setPlayers(getNicknames());
                else
                    message.setPlayers(playersInGames.get(player).getBoard().getNicknames());
                break;
            case LOGIN_SUCCESSFUL:
                message.addStringArguments(player);
                message.addIntegerArgument(lobby.size());
                message.setPlayers(getNicknames());
                break;
            default:
                break;
        }
        setChanged();
        notifyObservers(message);
    }
}
