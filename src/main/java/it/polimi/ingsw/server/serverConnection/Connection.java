package it.polimi.ingsw.server.serverConnection;

import java.util.List;

public interface Connection {

    void login(String nickname, int lobbySize);

    void loginError(String cause);

    void playerDisconnected(String nickname);

    void timerPing(int timeLeft);

    void createGame();

    void setSchemas(List<String> schemas);

    void setPrivateCard(String privateCard);

    void setPublicObjectives(List<String> publicObjectives);

    void setToolCards(List<Integer> toolCards);

    void chooseSchema(String schema);

    void setOpponentsSchemas(List<String> opponentsSchemas);

    void schemaCustomAccepted(String schema);

    void setOpponentsCustomSchemas(List<String> opponentsSchemas) ;

    void startRound();

    void startTurn(String nickname);

    void setActions(List<String> actions);

    void setDiceSpace(List<String> colours, List<Integer> values);

    void draftDiceAccepted();

    void insertDiceAccepted();

    void moveDiceAccepted();

    void pickDiceSpace(int index);

    void pickDiceSpaceError();

    void placeDiceSchema(String nickname, int row, int column, String colour, int value);

    void placeDiceSchemaError();

    void pickDiceSchema(String nickname, int row, int column);

    void pickDiceSchemaError();

    void useToolCardAccepted(int favors);

    void useToolCardError();

    void changeValueAccepted();

    void changeValueError();

    void placeDiceAccepted();

    void rollDiceAccepted(int value);

    void swapDiceAccepted();

    void pickDiceRoundTrack(int nRound, int nDice);

    void pickDiceRoundTrackError();

    void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values);

    void flipDiceAccepted(int value);

    void cancelUseToolCardAccepted(int favor);

    void placeDiceSpace(String colour, int value);

    void placeDiceSpaceAccepted();

    void rollDiceSpaceAccepted();

    void swapDiceBagAccepted(String colour, int value);

    void chooseValueAccepted();

    void chooseValueError();



}
