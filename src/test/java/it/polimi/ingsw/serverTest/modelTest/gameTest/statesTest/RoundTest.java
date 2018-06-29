package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;


import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.END_TURN;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private List<Player> players = new ArrayList <Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    private Round round2;
    private VirtualView view = new VirtualView();
    private List action = new ArrayList();
    private GameMultiplayer game;


    private void roundInit(){
        List<Schema> schemas = new ArrayList<>();
        Schema schema = new Schema();
        try {
            schema =buildSchema(1);
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
        game = new GameMultiplayer(players);
        board = game.getBoard();
        board.setObserver(view);
        view.setConnection(new Connected());
        round = new Round(player,board,null, game);
        round2 = new Round(player2,board,null, game);
    }

    @Test
    void ChangeStateCheck(){
        roundInit();

        round.roundInit();

        //Round switch states correctly
        assertTrue(round.getCurrentState().equals("ExtractDiceState"));
        //action.add("UseCard");
        //round.execute(action);
        //assertTrue(round.getCurrentState().toString() == "UseToolCardState");
        action.clear();
        action.add("InsertDice");
        action.add("0");
        action.add("0");
        action.add("0");
        round.execute(action);
        assertTrue(round.getCurrentState().equals("InsertDiceState"));
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
        assertTrue(round.getCurrentState().equals("EndTurnState"));
    }

    @Test
    void ChangeCurrentPlayerCheck (){
        roundInit();
        round.roundInit();
        round2.roundInit();
        action.add("EndTurn");

        //Round starts with the correct player
        assertSame(player,round.getCurrentPlayer());
        assertSame(player2,round2.getCurrentPlayer());
        assertEquals(0,round.getTurnNumber());

        //Round change the currentPlayer correctly
        round.execute(action);
        assertSame(player2,round.getCurrentPlayer());
        round.execute(action);
        assertSame(player3,round.getCurrentPlayer());
        round.execute(action);
        assertSame(player3,round.getCurrentPlayer());
        round.execute(action);
        assertSame(player2,round.getCurrentPlayer());

    }

    @Test
    void disconnectPlayer() {
        roundInit();
        round.roundInit();
        Player currentPlayer = round.getCurrentPlayer();
        int turnNumber = round.getTurnNumber();

        round.disconnectPlayer();

        assertNotSame(currentPlayer,round.getCurrentPlayer());
        assertEquals(turnNumber + 1, round.getTurnNumber());
        assertFalse(currentPlayer.isConnected());

    }
}
