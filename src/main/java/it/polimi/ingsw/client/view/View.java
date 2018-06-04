package it.polimi.ingsw.client.view;

import java.util.List;

public interface View {
    void setScene(String scene);
    void startScene();
    void login(String str);
    void setHandler(Handler hand);
    String getName();
    void timerPing(String time);
    void createGame();
    void setSchemas(List<String> schemas);
    void setPrivateCard(String colour);
    void setPublicObjectives(List<String> cards);
    void setToolCards(List<String> cards);
    void playerConnected(String name);
    void playerDisconnected(String name);
    void chooseSchema(String name);
    void setOpponentsSchemas(List <String>schemas);
    void setNumberPlayer(int nPlayer);
    void startRound() ;
    void startTurn(String name) ;
    void setActions(List <String> actions);
    void setDiceSpace(List <String> dices);
    void insertDiceAccepted();
    void draftDiceAccepted();
    void moveDiceAccepted();

    void pickDiceSpace(List action) throws InterruptedException;
    void pickDiceSpaceError();

    void placeDiceSchema(List action);
    void placeDiceSchemaError();

    void pickDiceSchema(List action);
    void pickDiceSchemaError();

    void useToolCardAccepted();
    void useToolCardError();
}