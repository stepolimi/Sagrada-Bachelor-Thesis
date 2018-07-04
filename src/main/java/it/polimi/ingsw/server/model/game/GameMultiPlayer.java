package it.polimi.ingsw.server.model.game;


import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.board.DeckSchemas;
import it.polimi.ingsw.server.model.cards.decks.DeckPrivateObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckPublicObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckToolsCard;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.timer.GameTimer;
import it.polimi.ingsw.server.model.timer.TimedComponent;
import it.polimi.ingsw.server.set.up.TakeDataFile;
import it.polimi.ingsw.server.virtual.view.VirtualView;


import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static it.polimi.ingsw.server.costants.Constants.MAX_SCHEMA_DICES;
import static it.polimi.ingsw.server.costants.LogConstants.*;
import static it.polimi.ingsw.server.costants.MessageConstants.SET_RANKINGS;
import static it.polimi.ingsw.server.costants.MessageConstants.SET_WINNER;
import static it.polimi.ingsw.server.costants.NameConstants.LOBBY_TIMER;
import static it.polimi.ingsw.server.costants.NameConstants.SCHEMA_TIMER;
import static it.polimi.ingsw.server.costants.SetupConstants.CONFIGURATION_FILE;
import static it.polimi.ingsw.server.costants.TimerConstants.SCHEMAS_TIMER_PING;

public class GameMultiPlayer extends Observable implements TimedComponent {
    private final List<Player> players;
    private final Board board;
    private final RoundManager roundManager;
    private Timer timer;
    private Long startingTime = 0L;
    private List<Player> rankings;
    private boolean ended;
    private final int lobbyTimerValue;
    private final int schemaTimerValue;

    public GameMultiPlayer(List<Player> players) {
        TakeDataFile timerConfig = new TakeDataFile(CONFIGURATION_FILE);
        lobbyTimerValue = Integer.parseInt(timerConfig.getParameter(LOBBY_TIMER));
        schemaTimerValue = Integer.parseInt(timerConfig.getParameter(SCHEMA_TIMER));
        this.players = new ArrayList<>();
        this.players.addAll(players);
        board = new Board(players);
        board.addObserver(VirtualView.getVirtualView());
        roundManager = new RoundManager(board, this);
        ended = false;
    }

    /**
     * Extracts and sets private objectives, public objectives, tool cards and 4 schema for each player.
     * Makes the timer for schema's choice start.
     */
    public void gameInit() {
        //set one set of 3 Public Objective and one of 3 Tool Cards in the board
        DeckPublicObjective deckPublic = new DeckPublicObjective();
        DeckToolsCard deckTools = new DeckToolsCard();
        board.setDeckPublic(deckPublic.extract());
        board.setDeckTool(deckTools.getToolCards());

        //set a private objective and a set of 4 Schemas for each player
        DeckPrivateObjective deckPrivate = new DeckPrivateObjective(players.size());
        DeckSchemas deckSchemas = new DeckSchemas(players.size());
        players.forEach(p -> {
            p.addObserver(VirtualView.getVirtualView());
            p.setPrCard(deckPrivate.extract(board.getIndex(p)));
            p.setSchemas(deckSchemas.deliver(board.getIndex(p)));
            board.addPrivate(p.getPrCard());
        });
        startingTime = System.currentTimeMillis();
        GameTimer schemaTimer = new GameTimer(schemaTimerValue, this);
        timer = new Timer();
        timer.schedule(schemaTimer, 0L, 5000L);
    }

    /**
     * Generate the rankings of players at the end of the game.
     * @param lastPlayer last player of the game.
     */
    public void endGame(Player lastPlayer) {
        Log.getLogger().addLog(CALCULATING_POINTS, Level.INFO,this.getClass().getName(),GAME_MULTI_PLAYER_END_GAME);
        if(timer!= null)
            timer.cancel();
        calculateScores();

        rankings = players.stream()
                .filter(Player::isConnected)
                .sorted(Comparator.comparing(Player::getScore).reversed())
                .collect(Collectors.toList());

        sortRankings(lastPlayer);

        players.forEach(player -> {
            if(!player.isConnected())
                rankings.add(player);
        });

        rankings.forEach(player -> Log.getLogger().addLog(player.getNickname() +": " + player.getScore(),Level.INFO,this.getClass().getName(),GAME_MULTI_PLAYER_END_GAME));
        notifyChanges(SET_WINNER);
        notifyChanges(SET_RANKINGS);
        ended = true;
    }

    private void sortRankings(Player lastPlayer){
        for (int i = 0; i < rankings.size() - 1; i++) {
            Player player1 = rankings.get(i);
            Player player2 = rankings.get(i + 1);
            if (player2.getScore() == player1.getScore()) {
                if (player2.getPrCard().scoreCard(player1.getSchema()) > player1.getPrCard().scoreCard(player2.getSchema())) {
                    rankings.remove(player2);
                    rankings.add(i, player2);
                } else if (player2.getPrCard().scoreCard(player2.getSchema()) == player1.getPrCard().scoreCard(player1.getSchema())) {
                    if (player2.getFavour() > player1.getFavour() || ((player2.getFavour() == player1.getFavour()) && player2.equals(lastPlayer))) {
                        rankings.remove(player2);
                        rankings.add(i, player2);
                    }
                }
            }
        }
    }

    /**
     * Calculates score of every connected player at the end of the game
     */
    private void calculateScores() {
        players.stream()
                .filter(Player::isConnected)
                .filter(player -> player.getSchema() != null)
                .forEach(player -> {
                    int score;
                    Schema schema = player.getSchema();
                    score = board.getDeckPublic()
                            .stream()
                            .mapToInt(card -> card.scoreCard(schema))
                            .sum();
                    score += player.getPrCard().scoreCard(schema);
                    score -= MAX_SCHEMA_DICES - schema.getSize();
                    score += player.getFavour();
                    if (score < 0)
                        score = 0;
                    player.setScore(score);
                    Log.getLogger().addLog(SCORE_OF + player.getNickname() + IS + score,Level.INFO,this.getClass().getName(),GAME_MULTI_PLAYER_CALCULATE_SCORE);
                });
    }

    /**
     * Sets the player as connected sends all game information to him.
     * @param nickname is the name of the player that is going to reconnect to the game
     */
    public void reconnectPlayer(String nickname) {
        Player player = board.getPlayer(nickname);
        player.setConnected(true);
        player.reconnectPlayer();
        if(board.getDiceSpace() != null)
            board.getDiceSpace().reconnectPlayer(player);
        board.getRoundTrack().reconnectPlayer(player);
        board.reconnectPlayer(player);
    }

    public List<Player> getRankings() { return rankings; }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }

    public Timer getTimer() {
        return timer;
    }

    /**
     * Set's a default schema to each player that has not choose one when timer elapses
     */
    public void timerElapsed() {
        Log.getLogger().addLog(SCHEMA_TIMER_ELAPSED,Level.INFO,this.getClass().getName(),GAME_MULTI_PLAYER_TIMER_ELAPSED);
        players.stream()
                .filter(p -> p.getSchema() == null)
                .forEach(p -> {
                    p.setSchema(p.getSchemas().get(0).getName());
                    board.addDefaultSchema(p.getSchema());
                });
        roundManager.setFirstPlayer();
        roundManager.startNewRound();
    }

    /**
     * Notifies the ping of the timer to the players.
     */
    public void timerPing(){ notifyChanges(SCHEMAS_TIMER_PING); }

    public boolean isEnded() { return ended; }

    /**
     * Notifies different changes to the observer
     * @param string head of the message that will be sent to the observer
     */
    private void notifyChanges(String string) {
        Message message = new Message(string);

        switch (string) {
            case SCHEMAS_TIMER_PING:
                message.addIntegerArgument((int) (lobbyTimerValue - (System.currentTimeMillis() - startingTime) / 1000));
                message.setPlayers(board.getNicknames());
                break;
            case SET_WINNER:
                message.addStringArguments(rankings.get(0).getNickname());
                message.setPlayers(players.stream().filter(Player::isConnected).map(Player::getNickname).collect(Collectors.toList()));
                break;
            case SET_RANKINGS:
                rankings.forEach(player -> {
                    message.addStringArguments(player.getNickname());
                    message.addIntegerArgument(player.getScore());
                });
                message.setPlayers(players.stream().filter(Player::isConnected).map(Player::getNickname).collect(Collectors.toList()));
                break;
            default:
                break;
        }
        setChanged();
        notifyObservers(message);
    }


}
