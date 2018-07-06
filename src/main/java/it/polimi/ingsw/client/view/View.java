package it.polimi.ingsw.client.view;

import java.util.List;

public interface View {
    void setScene(String scene);

    void startScene();

    void login(String str);

    void setHandler(Handler hand);

    String getName();

    void timerPing(String time);

    void turnTimerPing(int time);

    void createGame();

    void setSchemas(List<String> schemas);

    void setPrivateCard(String colour);

    void setPublicObjectives(List<String> cards);

    void setToolCards(List<Integer> cards);

    void playerConnected(String name);

    void playerDisconnected(String name);

    void chooseSchema(String name);

    void setOpponentsSchemas(List<String> schemas);

    void setNumberPlayer(int nPlayer);

    void startRound();

    void startTurn(String name);

    void setActions(List<String> actions);

    void setDiceSpace(List<String> colours, List<Integer> values);

    void insertDiceAccepted();

    void draftDiceAccepted();

    void moveDiceAccepted();

    void moveDiceError();

    void pickDiceSpace(int index) throws InterruptedException;

    void pickDiceSpaceError();

    void placeDiceSchema(String nickname, int row, int column, String colour, int value);

    void placeDiceSchemaError();

    void pickDiceSchema(String nickname, int row, int column);

    void pickDiceSchemaError();

    void useToolCardAccepted(int favor);

    void useToolCardError();

    void changeValueAccepted();

    void changeValueError();

    void placeDiceAccepted();

    void rollDiceAccepted(int value);

    void pickDiceRoundTrack(int nRound, int nDice);

    void pickDiceRoundTrackError();

    void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values);

    void swapDiceAccepted();

    void cancelUseToolCardAccepted(int favor);

    void flipDiceAccepted(int value);

    void placeDiceSpaceAccepted();

    void placeDiceSpace(String colour, int value);

    void rollDiceSpaceAccepted();

    void swapDiceBagAccepted(String colour, int value);

    void chooseValueAccepted();

    void chooseValueError();

    void schemaCustomAccepted(String name);

    void setOpponentsCustomSchemas(List<String> opponentsSchemas);

    void setWinner(String nickname);

    void setRankings(List<String> players, List<Integer> scores);

    void setSchemasOnReconnect(List<String> players, List<String> schemas);

}