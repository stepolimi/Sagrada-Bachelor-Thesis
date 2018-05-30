package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.costants.TimerCostants;
import it.polimi.ingsw.server.model.board.SetSchemas;
import it.polimi.ingsw.server.model.cards.decks.DeckPrivateObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckPublicObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckToolsCard;
import it.polimi.ingsw.server.model.game.states.RoundManager;
import it.polimi.ingsw.server.model.rules.RulesManager;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.timer.GameTimer;
import it.polimi.ingsw.server.timer.TimedComponent;

import java.util.*;

import static it.polimi.ingsw.costants.LoginMessages.*;
import static it.polimi.ingsw.costants.LoginMessages.TIMER_PING;
import static it.polimi.ingsw.costants.TimerCostants.SCHEMA_TIMER_VALUE;
import static it.polimi.ingsw.costants.TimerCostants.SCHEMAS_TIMER_PING;

public class GameMultiplayer extends Observable implements TimedComponent {
    private List<Player> players;
    private Board board;
    private Observer obs;
    private RoundManager roundManager;
    private RulesManager rulesManager;
    private GameTimer schemaTimer;
    private Timer timer;
    private Long startingTime = 0L;

    public GameMultiplayer( List <Player> players){
        this.players = new ArrayList<Player>();
        this.players.addAll(players);
        this.board= new Board(players);
        this.roundManager = new RoundManager(board);
        this.rulesManager = new RulesManager();
    }

    public void setObserver(Observer obs){
        this.obs = obs;
        board.addObserver(obs);
        board.setObserver(obs);
        roundManager.setObserver(obs);
    }

    public void gameInit(){
        //set one set of 3 Public Objective and one of 3 Tool Cards in the board
        DeckPublicObjective deckPublic = new DeckPublicObjective();
        DeckToolsCard deckTools = new DeckToolsCard();
        board.setDeckPublic(deckPublic.extract(players.size()));
        board.setDeckTool(deckTools.extract());

        //set a private objective and a set of 4 Schemas for each player
        DeckPrivateObjective deckPriv = new DeckPrivateObjective();
        SetSchemas deckSchemas = new SetSchemas(players.size());
        for(Player p: players) {
            p.setObserver(obs);
            p.addObserver(obs);
            p.setPrCard(deckPriv.extract());
            p.setSchemas(deckSchemas.deliver(getBoard().getIndex(p)));
            board.addPrivate(p.getPrCard());
        }

        startingTime = System.currentTimeMillis();
        schemaTimer = new GameTimer(SCHEMA_TIMER_VALUE,this);
        timer = new Timer();
        timer.schedule(schemaTimer,0L,5000L);
    }

    public void endGame(){
        setChanged();
        notifyObservers();
    }
    public List<Player> getPlayers() { return players; }

    public Board getBoard() { return board; }

    public RoundManager getRoundManager() { return roundManager; }

    public RulesManager getRulesManager() { return rulesManager; }

    public Timer getTimer(){ return timer;}

    public void notifyChanges(String string) {
        List action = new ArrayList();
        if (string.equals(TIMER_ELAPSED)) {
            System.out.println("Choosing schema timer elapsed\n"+"---");
            for(Player p: players){
                if(p.getSchema() == null)
                    p.setSchema(p.getSchemas().get(0).getName());
            }
            roundManager.setFirstPlayer();
            roundManager.startNewRound();
        } else if (string.equals(TIMER_PING)) {
            action.add(SCHEMAS_TIMER_PING);
            action.add(((Long) (TimerCostants.LOBBY_TIMER_VALUE - (System.currentTimeMillis() - startingTime) / 1000)).toString());
            setChanged();
            notifyObservers(action);
        }
    }
}
