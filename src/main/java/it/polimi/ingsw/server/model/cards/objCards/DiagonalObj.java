package it.polimi.ingsw.server.model.cards.objCards;

import it.polimi.ingsw.server.model.board.Schema;

public class DiagonalObj extends ObjectiveCard {

    private String name;
    private String description;

    public DiagonalObj(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @Override
    public int ScoreCard(Schema sch) {
        int score = 0;
        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (sch.getTable(i, j).getDice() != null) {
                    if((j + 1 < 5) && (i + 1 < 4) && sch.getTable(i + 1, j + 1).getDice() != null) {
                        if ((j + 1 < 5) && (i + 1 < 4) && sch.getTable(i, j).getDice().getColour()
                                == sch.getTable(i + 1, j + 1).getDice().getColour()) {
                            score++;
                            flag = true;
                        }
                if((i - 1 > 0) && (j + 1 < 4) && sch.getTable(i - 1, j + 1).getDice() != null){
                        if ((i - 1 > 0) && (j + 1 < 4) && sch.getTable(i, j).getDice().getColour()
                                == sch.getTable(i - 1, j + 1).getDice().getColour()) {
                            score++;
                            flag = true;
                        }
                        if (flag) {
                            score++;
                            flag = false;
                        }
                    }
                }
            }

            }
        }

        return score;
    }

    @Override
    public String getName(){ return this.name; }

    @Override
    public String toString(){
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src= src + "|" + this.name.toString() + "\n" + "|" + this.description + "\n" ;
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

}

    public void dump(){
        System.out.println(this);
    }
}