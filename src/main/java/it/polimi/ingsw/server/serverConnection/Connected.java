package it.polimi.ingsw.server.serverConnection;

import java.util.*;

public class Connected {
    private HashMap<String, Connection> users = new HashMap<>();

    public boolean checkUsername(String str) {
        if(users.keySet().contains(str)){
            System.out.println("connection failed: invalid username\n" + " ---");
            return false;
        }
        return true;
    }

    public void addPlayer(String user,Connection connection){
        if(!users.keySet().contains(user))
            users.put(user,connection);
        System.out.println(users);
    }

    public boolean removePlayer(String user) {
        if(users.keySet().contains(user)) {
            users.remove(user);
            return true;
        }
        return false;
    }


    public void login(String nickname, int lobbySize) {
        users.forEach((name,connection) -> connection.login(nickname, lobbySize));
    }

    public void reconnectPlayer(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.createGame();
    }

    public void loginError(String nickname, String cause) {
        Connection connection = users.get(nickname);
        if(connection != null) {
            connection.loginError(cause);
            removePlayer(nickname);
        }
    }

    public void playerDisconnected(String nickname) {
        users.forEach((name,connection) -> connection.playerDisconnected(nickname));
    }

    public void timerPing(int timeLeft) {
        users.forEach((name,connection) -> connection.timerPing(timeLeft));
    }

    public void createGame() {
        users.forEach((name,connection) -> connection.createGame());
    }

    public void setSchemas(String nickname, List<String> schemas) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.setSchemas(schemas);
    }

    public void setPrivateCard(String nickname, String privateCard) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.setPrivateCard(privateCard);
    }

    public void setPublicObjectives(List<String> publicObjectives) {
        users.forEach((name,connection) -> connection.setPublicObjectives(publicObjectives));
    }

    public void setToolCards(List<Integer> toolCards) {
        users.forEach((name,connection) -> connection.setToolCards(toolCards));
    }

    public void chooseSchema(String nickname, String schema) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.chooseSchema(schema);
    }

    public void setOpponentsSchemas(List<String> opponentsSchemas) {
        users.forEach((name,connection) -> connection.setOpponentsSchemas(opponentsSchemas));
    }

    public void schemaCustomAccepted(String nickname, String schema) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.schemaCustomAccepted(schema);
    }

    public void setOpponentsCustomSchemas(List<String> opponentsSchemas) {
        users.forEach((name,connection) -> connection.setOpponentsCustomSchemas(opponentsSchemas));
    }

    public void startRound() {
        users.forEach((name,connection) -> connection.startRound());
    }

    public void startTurn(String nickname) {
        users.forEach((name,connection) -> connection.startTurn(nickname));
    }

    public void setActions(String nickname, List<String> actions) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.setActions(actions);
    }

    public void setDiceSpace(List<String> colours, List<Integer> values) {
        users.forEach((name,connection) -> connection.setDiceSpace(colours, values));
    }

    public void draftDiceAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.draftDiceAccepted();
    }

    public void insertDiceAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.insertDiceAccepted();
    }

    public void moveDiceAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.moveDiceAccepted();
    }

    public void pickDiceSpace(Integer index) {
        users.forEach((name,connection) -> connection.pickDiceSpace(index));
    }

    public void pickDiceSpaceError(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.pickDiceSpaceError();
    }

    public void placeDiceSchema(String nickname, int row, int column, String colour, int value) {
        users.forEach((name,connection) -> connection.placeDiceSchema(nickname, row, column, colour, value));
    }

    public void placeDiceSchemaError(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.placeDiceSchemaError();
    }

    public void pickDiceSchema(String nickname, int row, int column) {
        users.forEach((name,connection) -> connection.pickDiceSchema(nickname, row, column));
    }

    public void pickDiceSchemaError(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.pickDiceSchemaError();
    }

    public void useToolCardAccepted(String nickname, int favors) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.useToolCardAccepted(favors);
    }

    public void useToolCardError(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.useToolCardError();
    }

    public void changeValueAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.changeValueAccepted();
    }

    public void changeValueError(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.changeValueError();
    }

    public void placeDiceAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.placeDiceAccepted();
    }

    public void rollDiceAccepted(String nickname, int value) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.rollDiceAccepted(value);
    }

    public void swapDiceAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.swapDiceAccepted();
    }

    public void pickDiceRoundTrack(int nRound, int nDice) {
        users.forEach((name,connection) -> connection.pickDiceRoundTrack(nRound, nDice));
    }

    public void pickDiceRoundTrackError(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.pickDiceRoundTrackError();
    }

    public void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) {
        users.forEach((name,connection) -> connection.placeDiceRoundTrack(nRound, colours, values));
    }

    public void flipDiceAccepted(String nickname, int value) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.flipDiceAccepted(value);
    }

    public void cancelUseToolCardAccepted(String nickname, int favors) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.cancelUseToolCardAccepted(favors);
    }

    public void placeDiceSpace(String colour, int value) {
        users.forEach((name,connection) -> connection.placeDiceSpace(colour, value));
    }

    public void placeDiceSpaceAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.placeDiceSpaceAccepted();
    }

    public void rollDiceSpaceAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.rollDiceSpaceAccepted();
    }

    public void swapDiceBagAccepted(String nickname, String colour, int value) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.swapDiceBagAccepted(colour, value);
    }

    public void chooseValueAccepted(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.chooseValueAccepted();
    }

    public void chooseValueError(String nickname) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.chooseValueError();
    }

    public void setWinner(String nickname) {
        users.forEach((name,connection) -> connection.setWinner(nickname));
    }

    public void setRankings(List<String> players, List<Integer> scores) {
        users.forEach((name,connection) -> connection.setRankings(players, scores));
    }

    public void setSchemasOnReconnect(String nickname, List<String> players, List<String> schemas) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.setSchemasOnReconnect(players, schemas);
    }

    public void setPublicObjectivesOnReconnect(String nickname, List<String> schemas) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.setPublicObjectives(schemas);
    }

    public void setToolCardsOnReconnect(String nickname, List<Integer> toolCards) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.setToolCards(toolCards);
    }

    public void setDiceSpaceOnReconnect(String nickname, List<String> colours, List<Integer> values) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.setDiceSpace(colours, values);
    }

    public void placeDiceRoundTrackOnReconnect(String nickname, int nRound, List<String> colours, List<Integer> values) {
        Connection connection = users.get(nickname);
        if(connection != null)
            connection.placeDiceRoundTrack(nRound, colours, values);
    }

}
