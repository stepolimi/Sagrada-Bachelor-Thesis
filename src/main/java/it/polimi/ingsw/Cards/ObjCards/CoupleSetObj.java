package it.polimi.ingsw.Cards.ObjCards;

import it.polimi.ingsw.Schema;

public class CoupleSetObj extends ObjectiveCard {
    private String name;
    private String description;
    private int a;
    private int b;

    public CoupleSetObj(String name, String description, int a, int b) {
        this.name = name;
        this.description = description;
        this.a = a;
        this.b = b;
    }

    @Override
    public int ScoreCard(Schema sch) {

        int count1 = 0;
        int count2 = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if(sch.getTable(i,j).getDice() != null) {
                    if (sch.getTable(i, j).getDice().getValue() == this.a)
                        count1++;
                    else if (sch.getTable(i, j).getDice().getValue() == this.b)
                        count2++;
                }
            }
        }
        if (count1 < count2)
            return 2 * count1;

        else return 2 * count2;


    }


}