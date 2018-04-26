package it.polimi.ingsw;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoxTest {    //in every schema we have only boxes that could have OR colour OR number restriction, not both

    @Test
    void CorrectBoxFormat() {
        Box b = new Box(Colour.ANSI_RED, 0);
        boolean result = b.boxformat(b);
        assertTrue(result = true, "correct format");
    }

    void  WrongBoxFormat(){
        Box b = new Box(Colour.ANSI_RED, 4);
        boolean result = b.boxformat(b);
        assertFalse(result = false, "wrong box format. Box could not have number and colour restriction at " +
                "the same time");
    }



}

