package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.board.SetSchemas;
import it.polimi.ingsw.server.model.cards.decks.DeckPrivateObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckPublicObjective;
import it.polimi.ingsw.server.model.cards.decks.DeckToolsCard;
import it.polimi.ingsw.server.model.game.states.RoundManager;
import it.polimi.ingsw.server.model.rules.RulesManager;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameMultiplayer extends Observable {
    private List<Player> players;
    private Board board;
    private Observer obs;
    private RoundManager roundManager;
    private RulesManager rulesManager;

    public GameMultiplayer( List <Player> players){
        this.players = new ArrayList<Player>();
        this.players.addAll(players);
        this.board= new Board(players);
        this.roundManager = new RoundManager(board,obs);
        this.rulesManager = new RulesManager();
    }

    public void setObserver(Observer obs){
        this.obs = obs;
        board.addObserver(obs);
        board.setObserver(obs);
    }

    public void gameInit(){
        //set one set of 3 Public Objective and one of 3 Tool Cards in the board
        DeckPublicObjective deckPublic = new DeckPublicObjective();
        DeckToolsCard deckTools = new DeckToolsCard();
        board.setDeckpubl(deckPublic.extract(players.size()));
        board.setDeckTool(deckTools.extract());

        //set a private objective and a set of 4 Schemas for each player
        DeckPrivateObjective deckPriv = new DeckPrivateObjective();
        SetSchemas deckSchemas = new SetSchemas(players.size());
        for(Player p: players) {
            p.setObserver(obs);
            p.addObserver(obs);
            p.setPrCard(deckPriv.extract());
            p.setSchemas(deckSchemas.deliver(getBoard().getIndex(p)));
            board.addPriv(p.getPrCard());
        }
    }

    public void endGame(){
        setChanged();
        notifyObservers();
    }
    public List<Player> getPlayers() { return players; }

    public Board getBoard() { return board; }

    public RoundManager getRoundManager() { return roundManager; }

    public RulesManager getRulesManager() { return rulesManager; }
}
