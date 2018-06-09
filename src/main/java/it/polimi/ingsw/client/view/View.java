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

    void useToolCardAccepted(int favor);
    void useToolCardError();

    void changeValueAccepted();
    void changeValueError();

    void placeDiceAccepted();
    void rollDiceAccepted(int value);

    void pickDiceRoundTrack(List action);
    void pickDiceRoundTrackError();

    void placeDiceRoundTrack(List action);

    void swapDiceAccepted();

    void cancelUseToolCardAccepted(int favor);

    void flipDiceAccepted(int value);

    void placeDiceSpaceAccepted();

    void placeDiceSpace(List action);

    void rollDiceSpaceAccepted(List action);

    void swapDiceBagAccepted(List action);

    void chooseValueAccepted();
    void chooseValueError();
}