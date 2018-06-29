package it.polimi.ingsw.serverTest.modelTest.boardTest;

import it.polimi.ingsw.server.model.board.Box;
import it.polimi.ingsw.server.model.board.Colour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoxTest {    //in every schema we have only boxes that could have OR colour OR number restriction, not both

    @Test
    void CorrectBoxFormat() {
        Box b = new Box(Colour.ANSI_RED, 0);
        assertTrue(!((b.getNumber() != 0) && (b.getC() != null)), "correct format");
    }
    @Test
    void  WrongBoxFormat(){
        Box b = new Box(Colour.ANSI_RED, 4);
        assertFalse(!((b.getNumber() != 0) && (b.getC() != null)), "wrong box format. Box could not have " +
                "number and colour restriction at the same time");
    }
    @Test
    void string(){
        Box b = new Box(Colour.ANSI_RED, 4);

        assertTrue(b.toString().equals("[ 4 ]"));
    }

}

