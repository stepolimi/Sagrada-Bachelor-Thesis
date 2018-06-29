package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

public class RoundManager  {
    private Board board;
    private GameMultiplayer game;
    private Observer obs;
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

            round.addObserver(obs);
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

    public void setObserver(Observer obs){
        this.obs = obs;
    }
}
