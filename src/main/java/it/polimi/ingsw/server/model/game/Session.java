package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.costants.TimerConstants;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.timer.GameTimer;
import it.polimi.ingsw.server.model.timer.TimedComponent;

import java.util.*;

import static it.polimi.ingsw.server.costants.Constants.EVERYONE;
import static it.polimi.ingsw.server.costants.Constants.MAX_PLAYERS;
import static it.polimi.ingsw.server.costants.Constants.MIN_PLAYERS;
import static it.polimi.ingsw.server.costants.MessageConstants.*;

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

    public void setObserver (Observer obs){ this.obs = obs; }

    /**
     * Adds players in the game's lobby until it starts.
     * If the player is the second one, the timer get started.
     * When the lobby is full or the timer elapse, a new game is created.
     *
     * @param player name of the player that is going to join the lobby
     */
    public synchronized void joinPlayer(String player) {
        if(game == null) {
            if (lobby == null) {
                lobby = new ArrayList<>();
            }else
                for(Player p: lobby) {
                    if (p.getNickname().equals(player)) {
                        notifyChanges(LOGIN_ERROR,player);
                        return;
                    }
                }
            notifyChanges(LOGIN_SUCCESSFUL,player);
            lobby.add(new Player(player));
            System.out.println("connected\n" + "players in lobby: " + lobby.size() + "\n ---");
            if(lobby.size() == MIN_PLAYERS ) {
                startingTime = System.currentTimeMillis();
                lobbyTimer = new GameTimer(TimerConstants.LOBBY_TIMER_VALUE,this);
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
                    game.reconnectPlayer(p);
                    notifyChanges(RECONNECT_PLAYER,player);
                    return ;
                }
            }
            System.out.println("connection failed: a game is already running\n" + " ---");
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
            System.out.println(player + " disconnected:\n" + "players in lobby: \n" + lobby.size() + " ---");
            notifyChanges(LOGOUT,player);
        }
        else {
            if(game.getRoundManager().getRound()!= null && game.getRoundManager().getRound().getCurrentPlayer().getNickname().equals(player))
                game.getRoundManager().getRound().disconnectPlayer();
            else {
                for (Player p : game.getPlayers()) {
                    if (p.getNickname().equals(player)) {
                        p.setConnected(false);
                        System.out.println(player + " disconnected:\n" + "players still connected: " + game.getBoard().getConnected() + "\n ---");
                        notifyChanges(LOGOUT,player);
                    }
                }
                if (game.getBoard().getConnected() == 1) {
                    game.endGame(game.getRoundManager().getRound().getCurrentPlayer());
                }
            }
        }
    }

    public List<Player> getPlayers(){ return this.lobby; }

    public GameMultiplayer getGame() { return game; }

    /**
     * Creates and initializes a new game and sets it's observer
     */
    private synchronized void startGame(){
        if(game == null) {
            System.out.println("starting game\n" + " ---");
            game = new GameMultiplayer(lobby);
            game.setObserver(obs);
            game.addObserver(obs);
            game.gameInit();
            System.out.println("game started:\n" + "waiting for players to choose their schema\n" + " ---");
        }
    }

    /**
     * Notifies that the timer is elapsed to the observer and makes the game start
     */
    public void timerElapsed() {
        notifyChanges(START_GAME,EVERYONE);
        startGame();
    }

    public void timerPing(){
        notifyChanges(TIMER_PING,EVERYONE);
    }

    /**
     * Notifies different changes to the observer
     * @param string head of the message that will be sent to the observer
     * @param player name of the player to whom the message will be sent. Can be sent to all of them.
     */
    public void notifyChanges(String string,String player){
        List action = new ArrayList();

        switch (string) {
            case START_GAME:
                action.add(string);
                break;
            case TIMER_PING:
                action.add(string);
                action.add((int) (TimerConstants.LOBBY_TIMER_VALUE - (System.currentTimeMillis() - startingTime) / 1000));
                break;
            case LOGIN_ERROR:
                action.add(string);
                action.add(player);
                action.add("game");
                break;
            case RECONNECT_PLAYER:
            case LOGOUT:
                action.add(string);
                action.add(player);
                break;
            case LOGIN_SUCCESSFUL:
                action.add(string);
                action.add(player);
                action.add(lobby.size());
                break;
            default:
                break;
        }
        setChanged();
        notifyObservers(action);
    }


}
