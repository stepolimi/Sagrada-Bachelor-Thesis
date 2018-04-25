package it.polimi.ingsw;

public class Schema {
    private String name;
    private int difficult;
    private Box table [][];
    private static int rows = 4;
    private static int columns = 5;

    public Schema(int difficult,Box table[][],String name)
    {
        this.name = name;
        this.difficult = difficult;
        this.table = table;
    }


    public int getDifficult() {
        return difficult;
    }

    public void insertDice(int rows , int columns)
    {

    }
    /*public Dice removeDice(int rows,int columns)
    {

    }
    */
}
