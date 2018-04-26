package it.polimi.ingsw;

import com.google.gson.Gson;


import java.io.IOException;

public class Main {
    public static void main(String[] args){

      /*  int i;
        i= 5;
        Schema sch= new Schema();
        try {
            sch.schemaInit(sch, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        Gson g = new Gson();
        Dice d = new Dice(Colour.ANSI_GREEN,5);
        Dice d2 = new Dice(Colour.ANSI_BLUE,2);
        String stringa = "{\"name\":\"Kaleidoscopic Dream\",\"difficult\":4,\"table\":[[{\"c\":\"ANSI_YELLOW\",\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":1,\"full\":false}],[{\"c\":\"ANSI_GREEN\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"number\":4,\"full\":false}],[{\"number\":3,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_GREEN\",\"number\":0,\"full\":false}],[{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_YELLOW\",\"number\":0,\"full\":false}]]}";
        Schema s = g.fromJson(stringa,Schema.class);
       /* Dice d = new Dice(Colour.ANSI_GREEN,6);
        System.out.println(d);
        Box b = new Box(null,5);
        b.setDice(d);
        */
        System.out.println(s);
        s.insertDice(0,0,d);
        System.out.println(s);
        s.insertDice(1,1,d2);
        System.out.println(s);
        s.removeDice(0,0);
        System.out.println(s);

    }
}

