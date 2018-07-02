package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.timer.GameTimer;
import it.polimi.ingsw.server.model.timer.TimedComponent;
import it.polimi.ingsw.server.setUp.TakeDataFile;

import java.util.*;
import java.util.logging.Level;


import static it.polimi.ingsw.server.costants.Constants.EVERYONE;
import static it.polimi.ingsw.server.costants.Constants.MAX_PLAYERS;
import static it.polimi.ingsw.server.costants.Constants.MIN_PLAYERS;
import static it.polimi.ingsw.server.costants.MessageConstants.*;
import static it.polimi.ingsw.server.costants.NameCostants.LOBBY_TIMER;
import static it.polimi.ingsw.server.costants.SetupCostants.CONFIGURATION_FILE;

/**
 * Manages the process of game start; players can join/left the lobby before game starts.
 * Players that will leave the game after it's start will be set as "disconnected" but not removed from the game.
 */
public  class Session extends Observable implements TimedComponent {
    private List<Player> lobby ;
    private GameMultiplayer game;
    private GameTimer lobbyTimer;
    private Timer timer;
    private Long startingTime = 0L;
    private Observer obs;
    private int lobbyTimerValue;
    private TakeDataFile timerConfig;
    public void setObserver (Observer obs){ this.obs = obs; }

    public Session()
    {
        timerConfig = new TakeDataFile(CONFIGURATION_FILE);
        lobbyTimerValue = Integer.parseInt(timerConfig.getParameter(LOBBY_TIMER));
    }
    /**
     * Adds players in the game's lobby until it starts.
     * If the player is the second one, the timer get started.
     * When the lobby is full or the timer elapse, a new game is created.
     *
     * @param player name of the player that is going to join the lobby
     */
    public synchronized void joinPlayer(String player) {
        checkEndedGames();
        if(game == null || game.isEnded()) {
            if (lobby == null) {
                lobby = new ArrayList<>();
            }else
                for(Player p: lobby) {
                    if (p.getNickname().equals(player)) {
                        notifyChanges(LOGIN_ERROR,player);
                        return;
                    }
                }
            lobby.add(new Player(player));
            notifyChanges(LOGIN_SUCCESSFUL,player);
            Log.getLogger().addLog("connected\n" + "players in lobby: " + lobby.size() + "\n ---",Level.INFO,this.getClass().getName(),"joinPlayer");
            if(lobby.size() == MIN_PLAYERS ) {
                startingTime = System.currentTimeMillis();
                lobbyTimer = new GameTimer(lobbyTimerValue,this);
                timer = new Timer();
                timer.schedule(lobbyTimer,0L,5000L);
            }
            else if(lobby.size() == MAX_PLAYERS){
                timer.cancel();
                notifyChanges(START_GAME,EVERYONE);
                startGame();
            }
        }
        else {
            for(Player p: game.getPlayers()){
                if(p.getNickname().equals(player)){
                    notifyChanges(RECONNECT_PLAYER,player);
                    game.reconnectPlayer(p);
                    return ;
                }
            }
            Log.getLogger().addLog("connection failed: a game is already running\n" + " ---",Level.INFO,this.getClass().getName(),"joinPlayer");
            notifyChanges(LOGIN_ERROR,player);
        }
    }

    /**
     * Removes players from the lobby, if it's size reaches one player, the timer will be reset
     * @param player name of the player that is going to leave the lobby
     */
    public synchronized void removePlayer(String player){
        if(game == null) {
            for(int i=0; i<lobby.size(); i++)
                if(lobby.get(i).getNickname().equals(player)) {
                    lobby.remove(i);
                }
            if(lobby.size() == MIN_PLAYERS-1) {
                timer.cancel();
                startingTime = 0L;
            }
            Log.getLogger().addLog(player + " disconnected:\n" + "players in lobby: " + lobby.size() + "\n ---",Level.INFO,this.getClass().getName(),"removePlayer");
            notifyChanges(LOGOUT,player);
        }
        else {
            if(game.getRoundManager().getRound()!= null && game.getRoundManager().getRound().getCurrentPlayer().getNickname().equals(player))
                game.getRoundManager().getRound().disconnectPlayer();
            else {
                for (Player p : game.getPlayers()) {
                    if (p.getNickname().equals(player)) {
                        p.setConnected(false);
                        Log.getLogger().addLog(player + " disconnected:\n" + "players still connected: " + game.getBoard().getConnected() + "\n ---",Level.INFO,this.getClass().getName(),"removePlayer");
                        notifyChanges(LOGOUT,player);
                    }
                }
                if (game.getBoard().getConnected() == 1) {
                    game.endGame(game.getRoundManager().getRound().getCurrentPlayer());
                }
            }
        }
    }

    /**
     * Checks if there is already a game and if it is finished. In that case it sets the game at null and clears the lobby.
     */
    private void checkEndedGames(){
        if(game!=null && game.isEnded()){
            game = null;
            lobby.clear();
        }
    }

    public List<Player> getPlayers(){ return this.lobby; }

    private synchronized List<String> getNicknames(){
        List<String> nicknames = new ArrayList<>();
        lobby.forEach(player -> nicknames.add(player.getNickname()));
        return nicknames;
    }

    public GameMultiplayer getGame() { return game; }

    /**
     * Creates and initializes a new game and sets it's observer
     */
    private synchronized void startGame(){
        if(game == null) {
            Log.getLogger().addLog("starting game\n" + " ---",Level.INFO,this.getClass().getName(),"startGame");
            game = new GameMultiplayer(lobby);
            game.setObserver(obs);
            game.addObserver(obs);
            game.gameInit();
            Log.getLogger().addLog("game started:\n" + "waiting for players to choose their schema\n" + " ---",Level.INFO,this.getClass().getName(),"startGame");
        }
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
    public void notifyChanges(String string,String player){
        Message message = new Message(string);

        switch (string) {
            case START_GAME:
                message.setPlayers(getNicknames());
                break;
            case TIMER_PING:
                message.addIntegerArgument((int) (lobbyTimerValue - (System.currentTimeMillis() - startingTime) / 1000));
                message.setPlayers(getNicknames());
                break;
            case LOGIN_ERROR:
                message.addStringArguments(player);
                message.addStringArguments("game");
                message.addPlayer(player);
                break;
            case RECONNECT_PLAYER:
                message.addPlayer(player);
                break;
            case LOGOUT:
                message.addStringArguments(player);
                message.setPlayers(getNicknames());
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
