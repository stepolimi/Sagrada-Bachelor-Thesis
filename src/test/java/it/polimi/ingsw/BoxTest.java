package it.polimi.ingsw;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoxTest {    //in every schema we have only boxes that could have OR colour OR number restriction, not both

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


}

