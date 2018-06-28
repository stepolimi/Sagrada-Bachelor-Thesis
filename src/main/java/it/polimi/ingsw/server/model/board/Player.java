//it's the player class with every attributes to report his status during the game (about his turn,
//if it's connected ecc) , and the other object (privateCard, favour and his schema)
package it.polimi.ingsw.server.model.board;
import it.polimi.ingsw.server.model.cards.PrivateObjective;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import static it.polimi.ingsw.server.costants.MessageConstants.*;

public class Player extends Observable {
    private String nickname;
    private Schema schema;
    private int favour;
    private boolean connected;
    private PrivateObjective prCard;
    private int score;
    private boolean myTurn;
    private Observer obs;
    private List<Schema> schemas = new ArrayList<>();


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

    /**
     * Sets the schema which have the specified name as the schema of the player, adds the observer to it and sends a
     * confirm message to the player.
     * @param name is the name of the schema that the player has chosen.
     */
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

    /**
     * Sets the specified schema as the schema of the player, adds the observer to it and sends a confirm message to the player.
     * @param schema is the schema that the player has chosen.
     */
    public void setSchema(Schema schema) {
        this.schema = schema;
        favour = schema.getDifficult();
        schema.setPlayer(this);
        schema.addObserver(obs);
        notifyChanges(APPROVED_SCHEMA);
    }

    /**
     * Sets the specified schema as the schema of the player, adds the observer to it and sends a confirm message to the player.
     * @param schema is the schema that the player has chosen.
     */
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

    /**
     * Increment's the player's favors by the specified value.
     * @param value value to be decremented the player's favors.
     */
    public void decrementFavor(int value) {
        this.favour -= value;
    }

    /**
     * Increment's the player's favors by the specified value.
     * @param value value to be decremented the player's favors.
     * */
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

    /**
     * Sets the private objective to the player and notifies it to him.
     * @param prCard is the private objective to be set to the player.
     */
    public void setPrCard(PrivateObjective prCard) {
        this.prCard = prCard;
        notifyChanges(SET_PRIVATE_CARD);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) { this.score = score; }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * Sets the list of schema to the player and notifies it to him.
     * @param schemas is the list of schema extracted for the player.
     */
    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
        notifyChanges(SET_SCHEMAS);
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    /**
     * @return a list with the name of schemas that has been extracted for the player.
     */
    public List<String> getNameSchemas() {
        return schemas.stream()
                .map((Schema::getName))
                .collect(Collectors.toList());
    }

    /**
     * Sends the private objective's identifier to the player.
     */
    public void reconnectPlayer(){
        notifyChanges(SET_PRIVATE_CARD);
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

    /**
     * Notifies different changes to the observer.
     * @param string head of the message that will be sent to the observer.
     */
    public void notifyChanges(String string) {
        List<String> action = new ArrayList<>();

        switch (string) {
            case SET_SCHEMAS:
                schemas.forEach(sch -> action.add(sch.getName()));
                break;
            case SET_PRIVATE_CARD:
                action.add(prCard.getColour());
                break;
            case APPROVED_SCHEMA:
                action.add(schema.getName());
                break;
            case APPROVED_SCHEMA_CUSTOM:
                action.add(schema.getName());
                break;
            default:
                break;
        }
        action.add(0,string);
        action.add(1,nickname);
        setChanged();
        notifyObservers(action);
    }
}

