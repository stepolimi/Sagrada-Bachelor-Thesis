package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.costants.TimerCostants;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.board.DeckSchemas;
import it.polimi.ingsw.server.model.cards.decks.DeckPrivateObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckPublicObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckToolsCard;
import it.polimi.ingsw.server.model.game.states.RoundManager;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.timer.GameTimer;
import it.polimi.ingsw.server.timer.TimedComponent;

import java.util.*;

import static it.polimi.ingsw.costants.LoginMessages.TIMER_PING;
import static it.polimi.ingsw.costants.TimerCostants.SCHEMA_TIMER_VALUE;
import static it.polimi.ingsw.costants.TimerCostants.SCHEMAS_TIMER_PING;
import static it.polimi.ingsw.server.serverCostants.Constants.MAX_SCHEMA_DICES;

public class GameMultiplayer extends Observable implements TimedComponent {
    private List<Player> players;
    private Board board;
    private Observer obs;
    private RoundManager roundManager;
    private GameTimer schemaTimer;
    private Timer timer;
    private Long startingTime = 0L;

    public GameMultiplayer(List<Player> players) {
        this.players = new ArrayList<>();
        this.players.addAll(players);
        this.board = new Board(players);
        this.roundManager = new RoundManager(board);
    }

    public void setObserver(Observer obs) {
        this.obs = obs;
        board.addObserver(obs);
        board.setObserver(obs);
        roundManager.setObserver(obs);
    }

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

    public void endGame() {
        System.out.println("calcolo punteggio");
        Optional<Player> winner;

        calculateScores();
        winner = players.stream()
                .filter(Player::isConnected)
                .reduce((player1,player2) -> player1.getScore() >= player2.getScore() ? player1 : player2);

        winner.ifPresent(player -> System.out.println("the winner is: " + winner.get().getNickname()) );


    }

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

                    player.setScore(score);
                    System.out.println("score of " + player.getNickname() + " is " + score);
                });
    }

    public void reconnectPlayer(Player player) {
        player.setConnected(true);

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

    public void notifyChanges(String string) {
        List action = new ArrayList<>();

        if (string.equals(TIMER_PING)) {
            action.add(SCHEMAS_TIMER_PING);
            action.add((int)(TimerCostants.LOBBY_TIMER_VALUE - (System.currentTimeMillis() - startingTime) / 1000));
            setChanged();
            notifyObservers(action);
        }
    }


}
