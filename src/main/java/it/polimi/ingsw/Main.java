package it.polimi.ingsw;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {

        Gson g = new Gson();

        Box t [][] = new Box [4][5];

        for(int i=0; i<4; i++) {
            for (int j = 0; j < 5; j++) {
                Box b = new Box(null, 0);
                t[i][j] = b;
            }
        }

        t[0][0].setNumber(0);
        t[0][0].setC(Colour.ANSI_YELLOW);

        t[0][1].setNumber(0);
        t[0][1].setC(Colour.ANSI_BLUE);

        t[0][2].setNumber(0);
        t[0][2].setC(null);

        t[0][3].setNumber(0);
        t[0][3].setC(null);

        t[0][4].setNumber(1);
        t[0][4].setC(null);

        t[1][0].setNumber(0);
        t[1][0].setC(Colour.ANSI_GREEN);

        t[1][1].setNumber(0);
        t[1][1].setC(null);

        t[1][2].setNumber(5);
        t[1][2].setC(null);

        t[1][3].setNumber(0);
        t[1][3].setC(null);

        t[1][4].setNumber(4);
        t[1][4].setC(null);

        t[2][0].setNumber(3);
        t[2][0].setC(null);

        t[2][1].setNumber(0);
        t[2][1].setC(null);

        t[2][2].setNumber(0);
        t[2][2].setC(Colour.ANSI_RED);

        t[2][3].setNumber(0);
        t[2][3].setC(null);

        t[2][4].setNumber(0);
        t[2][4].setC(Colour.ANSI_GREEN);

        t[3][0].setNumber(2);
        t[3][0].setC(null);

        t[3][1].setNumber(0);
        t[3][1].setC(null);

        t[3][2].setNumber(0);
        t[3][2].setC(null);

        t[3][3].setNumber(0);
        t[3][3].setC(Colour.ANSI_BLUE);

        t[3][4].setNumber(0);
        t[3][4].setC(Colour.ANSI_YELLOW);


        Schema s = new Schema(4,t,"Kaleidoscopic Dream");
        System.out.println(g.toJson(s));


    }
}

