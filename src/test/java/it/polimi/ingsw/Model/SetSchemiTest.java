package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetSchemiTest {
    SetSchemi set = new SetSchemi(3);

    public List<Schema> schemasList() {
        List<Schema> schemas = new ArrayList<Schema>();
        for (int i = 0; i < 12; i++) {
            try {
                schemas.add(new Schema().schemaInit(i));
                schemas.add(new Schema().schemaInit(i + 12));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return schemas;
    }

    @Test
    public void deliverTest(){
        List<Schema> schemas = schemasList();

        assertTrue(set.deliver(0).size()== 4);
        assertTrue(set.deliver(1).size()== 4);
        assertTrue(set.deliver(2).size()== 4);
        /*
        for(Schema s: set.deliver(0)){
            for(Schema s2: schemas)
                if(s.toString() == s2.toString()) {
                    found = true;
                }
            assertTrue(found);
        }*/
    }
}
