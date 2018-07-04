package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.virtual.view.VirtualView;

import java.util.Random;

import static it.polimi.ingsw.server.costants.Constants.TOT_ROUNDS;

public class RoundManager  {
    private final Board board;
    private final GameMultiPlayer game;
    private int firstPlayerIndex = 0;
    private int roundNum = 0;
    private Round round ;

    public RoundManager(Board board, GameMultiPlayer game){
        this.board = board;
        this.game = game;
        round = null;
    }

    /**
     * Create a new round and makes it start, calculates the next round's first player
     */
    public void startNewRound() {
        if(roundNum < TOT_ROUNDS){
            boolean playerConnected = false;
            do{
                if(board.getPlayerList().get(firstPlayerIndex).isConnected()) {
                    round = new Round(board.getPlayerList().get(firstPlayerIndex), board, this, game );
                    playerConnected = true;
                }
                if(firstPlayerIndex < board.numPlayers()-1)
                    firstPlayerIndex ++;
                else
                    firstPlayerIndex = 0;
            }while (!playerConnected);

            round.addObserver(VirtualView.getVirtualView());
            round.roundInit();
            roundNum ++;
        }
    }

    /**
     * Sets the first player of the game randomly
     */
    public void setFirstPlayer(){
        Random rand = new Random();
        firstPlayerIndex = rand.nextInt(board.numPlayers());
    }

    public int getRoundNumber(){return roundNum;}

    public Round getRound(){ return round;}
}
