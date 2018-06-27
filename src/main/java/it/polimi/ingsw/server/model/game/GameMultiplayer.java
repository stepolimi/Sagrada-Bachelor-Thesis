package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.costants.TimerConstants;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.board.DeckSchemas;
import it.polimi.ingsw.server.model.cards.decks.DeckPrivateObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckPublicObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckToolsCard;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.timer.GameTimer;
import it.polimi.ingsw.server.model.timer.TimedComponent;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.server.costants.Constants.MAX_SCHEMA_DICES;
import static it.polimi.ingsw.server.costants.MessageConstants.SET_RANKINGS;
import static it.polimi.ingsw.server.costants.MessageConstants.SET_WINNER;
import static it.polimi.ingsw.server.costants.MessageConstants.TIMER_PING;
import static it.polimi.ingsw.server.costants.TimerConstants.SCHEMA_TIMER_VALUE;
import static it.polimi.ingsw.server.costants.TimerConstants.SCHEMAS_TIMER_PING;

public class GameMultiplayer extends Observable implements TimedComponent {
    private List<Player> players;
    private Board board;
    private Observer obs;
    private RoundManager roundManager;
    private GameTimer schemaTimer;
    private Timer timer;
    private Long startingTime = 0L;
    private List<Player> rankings;

    public GameMultiplayer(List<Player> players) {
        this.players = new ArrayList<>();
        this.players.addAll(players);
        this.board = new Board(players);
        this.roundManager = new RoundManager(board, this);
    }

    /**
     * Adds Observer to Board and RoundManager.
     * @param obs observer to be set
     */
    public void setObserver(Observer obs) {
        this.obs = obs;
        board.addObserver(obs);
        board.setObserver(obs);
        roundManager.setObserver(obs);
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
        DeckPrivateObjective deckPriv = new DeckPrivateObjective(players.size());
        DeckSchemas deckSchemas = new DeckSchemas(players.size());
        players.forEach(p -> {
            p.setObserver(obs);
            p.addObserver(obs);
            p.setPrCard(deckPriv.extract(board.getIndex(p)));
            p.setSchemas(deckSchemas.deliver(board.getIndex(p)));
            board.addPrivate(p.getPrCard());
        });
        startingTime = System.currentTimeMillis();
        schemaTimer = new GameTimer(SCHEMA_TIMER_VALUE, this);
        timer = new Timer();
        timer.schedule(schemaTimer, 0L, 5000L);
    }

    /**
     * Generate the rankings of players at the end of the game.
     * @param lastPlayer last player of the game.
     */
    public void endGame(Player lastPlayer) {
        System.out.println("calcolo punteggio");

        calculateScores();

        rankings = players.stream()
                .filter(Player::isConnected)
                .sorted(Comparator.comparing(Player::getScore).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < rankings.size() - 1; i++) {
            Player player1 = rankings.get(i);
            Player player2 = rankings.get(i + 1);
            if (player2.getScore() == player1.getScore()) {
                if (player2.getPrCard().scoreCard(player1.getSchema()) > player1.getPrCard().scoreCard(player2.getSchema())) {
                    rankings.remove(player2);
                    rankings.add(i, player2);
                } else if (player2.getPrCard().scoreCard(player2.getSchema()) == player1.getPrCard().scoreCard(player1.getSchema())) {
                    if (player2.getFavour() > player1.getFavour()) {
                        rankings.remove(player2);
                        rankings.add(i, player2);
                    } else if ((player2.getFavour() == player1.getFavour()) && player2.equals(lastPlayer)) {
                        rankings.remove(player2);
                        rankings.add(i, player2);
                    }
                }
            }
        }

        players.forEach(player -> {
            if(!player.isConnected())
                rankings.add(player);
        });

        rankings.forEach(player -> System.out.println(player.getNickname() +": " + player.getScore()));
        notifyChanges(SET_WINNER);
        notifyChanges(SET_RANKINGS);
    }

    /**
     * Calculates score of every connected player at the end of the game
     */
    private void calculateScores() {
        players.stream()
                .filter(Player::isConnected)
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
                    System.out.println("score of " + player.getNickname() + " is " + score);
                });
    }

    /**
     * Sets the player as connected sends all game information to him.
     * @param player is the player that is going to reconnect to the game
     */
    public void reconnectPlayer(Player player) {
        player.setConnected(true);
        player.reconnectPlayer();
        board.getDiceSpace().reconnectPlayer(player);
        board.getRoundTrack().reconnectPlayer(player);
        board.reconnectPlayer(player);
    }

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
        System.out.println("Choosing schema timer elapsed\n" + "---");
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
     * Notifies different changes to the observer
     * @param string head of the message that will be sent to the observer
     */
    public void notifyChanges(String string) {
        List action = new ArrayList<>();

        switch (string) {
            case TIMER_PING:
                action.add(SCHEMAS_TIMER_PING);
                action.add((int) (TimerConstants.LOBBY_TIMER_VALUE - (System.currentTimeMillis() - startingTime) / 1000));
                break;
            case SET_WINNER:
                action.add(string);
                action.add(rankings.get(0).getNickname());
                break;
            case SET_RANKINGS:
                action.add(string);
                rankings.forEach(player -> {
                    action.add(player.getNickname());
                    action.add(player.getScore());
                });
                break;
            default:
                break;
        }
        setChanged();
        notifyObservers(action);
    }


}
