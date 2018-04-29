//it's the player class with every attributes to report his status during the game (about his turn,
//if it's connected ecc) , and the other object (privateCard, favour and his schema)
package it.polimi.ingsw;
import it.polimi.ingsw.Cards.PrivateObjective;

import java.util.List;

public class Player {
    private String nickname;
    private Schema schema;
    private int favour;
    // private dice  Dice;
    private boolean connected;
    private PrivateObjective prCard;
    private int score;
    private boolean myTurn;



    public Player(String nickname, Schema schema){
        this.nickname = nickname;
        this.favour = schema.getDifficult();
        this.connected = false;
        this.score = 0;
        this.myTurn = false;
    }
    public String getNickname() {
        return nickname;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public int getFavour() {
        return favour;
    }

    public void setFavour(int favour) {
        this.favour = favour;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public PrivateObjective getPrCard() {
        return prCard;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void takeSchema(List<Schema> sc, int index)
    {
        this.schema = sc.get(index);
    }

    public void setObjective(PrivateObjective po){
        this.prCard = po;
    }

    @Override
    public String toString() {
        String src = new String();
        src = src +"nickname:" + this.getNickname() + "\n";
        src = src +"Schema choosen:" + this.getSchema().getName() + "\n";
        src = src  +"score:" + this.getScore() + "\n";
        return src;
    }

    public void dump(){
        System.out.println(this);
    }
}

