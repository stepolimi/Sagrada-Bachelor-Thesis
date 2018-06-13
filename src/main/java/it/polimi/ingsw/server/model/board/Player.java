//it's the player class with every attributes to report his status during the game (about his turn,
//if it's connected ecc) , and the other object (privateCard, favour and his schema)
package it.polimi.ingsw.server.model.board;
import com.google.gson.Gson;
import it.polimi.ingsw.server.model.cards.PrivateObjective;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.costants.GameCreationMessages.*;

public class Player extends Observable {
    private String nickname;
    private Schema schema;
    private int favour;
    private boolean connected;
    private PrivateObjective prCard;
    private int score;
    private boolean myTurn;
    private Observer obs;
    private List<Schema> schemas = new ArrayList<Schema>();


    public Player(String nickname) {
        this.nickname = nickname;
        this.connected = true;
        this.score = 0;
        this.myTurn = false;
    }

    public void setObserver(Observer obs) {
        this.obs = obs;
    }

    public String getNickname() {
        return nickname;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(String name) {
        for (Schema s : schemas) {
            if (s.getName().equals(name)) {
                schema = s;
                favour = schema.getDifficult();
                schema.setPlayer(this);
                schema.addObserver(obs);
                notifyChanges(APPROVED_SCHEMA);
                return;
            }
        }
        notifyChanges(SET_SCHEMAS);
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
        favour = schema.getDifficult();
        schema.setPlayer(this);
        schema.addObserver(obs);
        notifyChanges(APPROVED_SCHEMA);
    }

    public void setCustomSchema(Schema schema) {
        this.schema = schema;
        favour = schema.getDifficult();
        schema.setPlayer(this);
        schema.addObserver(obs);
        notifyChanges(APPROVED_SCHEMA_CUSTOM);
    }

    public int getFavour() {
        return favour;
    }

    public void decrementFavor(int value) {
        this.favour -= value;
    }

    public void incrementFavor(int value) {
        this.favour += value;
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

    public void setPrCard(PrivateObjective prCard) {
        this.prCard = prCard;
        notifyChanges(SET_PRIVATE_CARD);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        notifyChanges("setScore");
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
        notifyChanges(SET_SCHEMAS);
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    public List<String> getNameSchemas() {
        List<String> nameSchemas = new ArrayList<String>();
        for (Schema s : schemas)
            nameSchemas.add(s.getName());
        return nameSchemas;
    }

    @Override
    public String toString() {
        String src = "";
        src = src + "nickname:" + this.getNickname() + "\n";
        src = src + "Schema choosen:" + this.getSchema().getName() + "\n";
        src = src + "score:" + this.getScore() + "\n";
        return src;
    }

    public void dump() {
        System.out.println(this);
    }

    public void notifyChanges(String string) {
        List<String> action = new ArrayList<String>();
        action.add(string);
        action.add(nickname);
        if (string.equals(SET_SCHEMAS))
            for (Schema s : schemas)
                action.add(s.getName());
        else if (string.equals(SET_PRIVATE_CARD))
            action.add(prCard.getColour());
        else if (string.equals(APPROVED_SCHEMA) || string.equals(APPROVED_SCHEMA_CUSTOM))
            action.add(schema.getName());
        else if (string.equals("setScore"))
            action.add(((Integer) score).toString());
        setChanged();
        notifyObservers(action);
    }
}

