package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class TestUnit{

    @Test
    void myFirstTest(){
        classe_prova i= new classe_prova();
        assertTrue(i.chiamata());
    }
}
