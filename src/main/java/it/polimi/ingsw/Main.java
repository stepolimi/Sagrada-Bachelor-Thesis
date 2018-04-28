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
    /*   Schema s1 = new Schema();
       try{
           s1.schemaInit(s1, 23);

       }

       catch(IOException e){
           System.out.println(e);
       }
    */
    Dice d1 = new Dice(Colour.ANSI_RED,1);

    Dice d3 = new Dice(Colour.ANSI_PURPLE,3);
    Dice d4 = new Dice(Colour.ANSI_BLUE,4);
    Dice d5 = new Dice(Colour.ANSI_RED,5);
    Dice d6 = new Dice(Colour.ANSI_YELLOW,2);
    Dice d7 = new Dice(Colour.ANSI_RED,5);
    Dice d8 = new Dice(Colour.ANSI_RED,5);
        Dice d9 = new Dice(Colour.ANSI_BLUE,2);
        Dice d10 = new Dice(Colour.ANSI_RED,1);

        System.out.println(s);
        s.insertDice(0,0,d);
        s.insertDice(0,1,d1);
        System.out.println(s);
        s.insertDice(1,1,d2);
        System.out.println(s);
        s.insertDice(0,2,d3);
        s.insertDice(1,0,d4);
        s.insertDice(1,2,d5);
        s.insertDice(2,0,d6);
        s.insertDice(2,1,d7);
        s.insertDice(2,2,d8);
        s.insertDice(0,4,d9);
        s.insertDice(1,4,d10);
        System.out.println(s);
        System.out.println(s.nearDice(1,1)); // centro
        System.out.println(s.nearDice(0,1)); // alto
        System.out.println(s.nearDice(3,2)); // basso
        System.out.println(s.nearDice(0,0)); // alto sx
        System.out.println(s.nearDice(0,4)); // alto dx
        System.out.println(s.nearDice(1,4)); // dx
        System.out.println(s.nearDice(3,0)); // basso sx
        System.out.println(s.nearDice(1,0)); //sx
        System.out.println(s.nearDice(3,4));// dx basso
        DiceBag b = new DiceBag();

        for(int i=0;i<11;i++)
            b.extract(4);


      /*  int index= 0;
        GameMultiplayer game = new GameMultiplayer();
        game.execute();
      */
    }
}

