package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.DiceBag;
import org.junit.jupiter.api.Test;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class DiceBagTest {
    DiceBag db ;

    @Test
    void FinishDice()
    {
        db =  new DiceBag();
        for(int i=0;i<10;i++)
          db.extract(4);

        assertTrue("The DiceBag is empty",db.extract(4)==null);

    }

    @Test
    void ExtractDice()
    {
        List<Dice> extract;
        String str;
        int r = 18,y=18,p=18,g=18,b=18;

        db = new DiceBag();
        switch(db.takeDice().getcolour())
        {
            case ANSI_YELLOW:y--;break;
            case ANSI_PURPLE:p--;break;
            case ANSI_GREEN:g--;break;
            case ANSI_BLUE:b--;break;
            default:r--;
        }


        str= "dices in the dicebag:" + db.getDices().size() + "\n";
        str+= "Red:"+r+"\nGreen:"+g+"\nYellow:"+y+"\nBlue:"+b+"\nPurple:"+p ;
        assertEquals(str,db.toString());
    }

    @Test
    void NumberExtractDice()
    {
        int nPlayer = 4;
        db = new DiceBag();
        db.extract(nPlayer);
        assertEquals(db.getDices().size(),90-(nPlayer*2 +1));
    }

    @Test
    void  initial_quantity(){
        db = new DiceBag();
        assertEquals(90, db.getDices().size());
    }

    @Test
    void final_quantity(){
        int nPlayer= 4;
        db = new DiceBag();
        for(int i = 0; i<10; i++){
            db.extract(nPlayer);

        }
        assertEquals(0, db.getDices().size());
    }

}
