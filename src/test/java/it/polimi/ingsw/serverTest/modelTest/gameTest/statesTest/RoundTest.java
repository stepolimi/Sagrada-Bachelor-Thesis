package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;


import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoundTest {
    private List<Player> players = new ArrayList <Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    private Round round2;
    private VirtualView view = new VirtualView();
    private List action = new ArrayList();


    private void TestInit(){
        List<Schema> schemas = new ArrayList<Schema>();
        Schema schema = new Schema();
        try {
            schema = schema.schemaInit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        schemas.add(schema);
        player.setObserver(view);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());
        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        board.setObserver(view);
        view.setConnection(new Connected());
    }

    @Test
    void ChangeStateCheck(){
        TestInit();
        round = new Round(player,board,null);
        round.roundInit();

        //Round switch states correctly
        assertTrue(round.getCurrentState().toString() == "ExtractDiceState" );
        //action.add("UseCard");
        //round.execute(action);
        //assertTrue(round.getCurrentState().toString() == "UseToolCardState");
        action.clear();
        action.add("InsertDice");
        action.add("0");
        action.add("0");
        action.add("0");
        round.execute(action);
        assertTrue(round.getCurrentState().toString() == "InsertDiceState");
        //action.add("RollDice");
        //round.execute(action);
        //assertTrue(round.getCurrentState().toString() == "RollDiceState");
        //round.execute("ChangeValue");
        //assertTrue(round.getCurrentState().toString() == "ChangeValueState");
        //action.add(0,"PlaceDice");
        //round.execute(action);
        //assertTrue(round.getCurrentState().toString() == "PlaceDiceState");
        action.add(0,"EndTurn");
        round.execute(action);
        assertTrue(round.getCurrentState().toString() == "EndTurnState");
    }

    @Test
    void ChangeCurrentPlayerCheck (){
        TestInit();
        round = new Round(player,board,null);
        round2 = new Round(player2,board,null);
        round.roundInit();
        round2.roundInit();
        action.add("EndTurn");

        //Round starts with the correct player
        assertTrue(round.getCurrentPlayer()== player);
        assertTrue(round2.getCurrentPlayer()== player2);
        assertTrue(round.getTurnNumber()== 0);

        //Round change the currentPlayer correctly
        round.execute(action);
        assertTrue(round.getCurrentPlayer()== player2);
        round.execute(action);
        assertTrue(round.getCurrentPlayer() == player3);
        round.execute(action);
        assertTrue(round.getCurrentPlayer() == player3);
        round.execute(action);
        assertTrue(round.getCurrentPlayer()== player2);

    }
}
