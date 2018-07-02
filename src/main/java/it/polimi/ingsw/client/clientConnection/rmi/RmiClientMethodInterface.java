package it.polimi.ingsw.client.clientConnection.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiClientMethodInterface extends Remote {

    void login(String nickname, int lobbySize) throws RemoteException;

    void loginError(String cause) throws RemoteException;

    void playerDisconnected(String nickname) throws RemoteException;

    void timerPing(int timeLeft) throws RemoteException;

    void createGame() throws RemoteException;

    void setSchemas(List<String> schemas) throws RemoteException;

    void setPrivateCard(String privateCard) throws RemoteException;

    void setPublicObjectives(List<String> publicObjectives) throws RemoteException;

    void setToolCards(List<Integer> toolCards) throws RemoteException;

    void chooseSchema(String schema) throws RemoteException;

    void setOpponentsSchemas(List<String> opponentsSchemas) throws RemoteException;

    void schemaCustomAccepted(String schema) throws RemoteException;

    void setOpponentsCustomSchemas(List<String> opponentsSchemas) throws RemoteException;

    void startRound() throws RemoteException;

    void startTurn(String nickname) throws RemoteException;

    void setActions(List<String> actions) throws RemoteException;

    void setDiceSpace(List<String> colours, List<Integer> values) throws RemoteException;

    void insertDiceAccepted() throws RemoteException;

    void draftDiceAccepted() throws RemoteException;

    void moveDiceAccepted() throws RemoteException;

    void moveDiceError() throws RemoteException;

    void pickDiceSpace(int index) throws RemoteException;

    void pickDiceSpaceError() throws RemoteException;

    void placeDiceSchema(String nickname, int row, int column, String colour, int value) throws RemoteException;

    void placeDiceSchemaError() throws RemoteException;

    void pickDiceSchema(String nickname, int row, int column) throws RemoteException;

    void pickDiceSchemaError() throws RemoteException;

    void useToolCardAccepted(int favors) throws RemoteException;

    void useToolCardError() throws RemoteException;

    void changeValueAccepted() throws RemoteException;

    void changeValueError() throws RemoteException;

    void placeDiceAccepted() throws RemoteException;

    void rollDiceAccepted(int value) throws RemoteException;

    void swapDiceAccepted() throws RemoteException;

    void pickDiceRoundTrack(int nRound, int nDice) throws RemoteException;

    void pickDiceRoundTrackError() throws RemoteException;

    void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) throws RemoteException;

    void flipDiceAccepted(int value) throws RemoteException;

    void cancelUseToolCardAccepted(int favors) throws RemoteException;

    void placeDiceSpace(String colour, int value) throws RemoteException;

    void placeDiceSpaceAccepted() throws RemoteException;

    void rollDiceSpaceAccepted() throws RemoteException;

    void swapDiceBagAccepted(String colour, int value) throws RemoteException;

    void chooseValueAccepted() throws RemoteException;

    void chooseValueError() throws RemoteException;

    void setWinner(String nickname) throws RemoteException;

    void setRankings(List<String> players, List<Integer> scores) throws RemoteException;

    void setSchemasOnReconnect(List<String> players, List<String> schemas) throws RemoteException;

    void ping() throws  RemoteException;
}
