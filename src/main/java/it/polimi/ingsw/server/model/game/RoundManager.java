package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.Random;

public class RoundManager  {
    private Board board;
    private GameMultiplayer game;
    private int firstPlayerIndex = 0;
    private int roundNum = 0;
    private Round round ;

    public RoundManager(Board board, GameMultiplayer game){
        this.board = board;
        this.game = game;
        round = null;
    }

    /**
     * Create a new round and makes it start, calculates the next round's first player
     */
    public void startNewRound() {
        if(roundNum <10){
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
