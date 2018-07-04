package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.server.log.Log;

import java.util.*;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.CONNECTED_CHECK_USERNAME;
import static it.polimi.ingsw.server.costants.LogConstants.INVALID_USERNAME;

public class Connected {
    private static Connected instance = null;
    private final HashMap<String, Connection> users = new HashMap<>();

    private Connected(){}

    public static synchronized Connected getConnected(){
        if(instance == null)
            instance = new Connected();
        return instance;
    }

    public boolean checkUsername(String str) {
        if(users.keySet().contains(str)){
            Log.getLogger().addLog(INVALID_USERNAME , Level.INFO,this.getClass().getName(),CONNECTED_CHECK_USERNAME);
            return false;
        }
        return true;
    }

    public void addPlayer(String user,Connection connection){
        if(!users.keySet().contains(user))
            users.put(user,connection);
    }

    public boolean removePlayer(String user) {
        if(users.keySet().contains(user)) {
            users.remove(user);
            return true;
        }
        return false;
    }


    public void login(List<String> nicknames, String nickname, int lobbySize) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if(connection != null)
                connection.login(nickname, lobbySize);
        });
    }

    public void reconnectPlayer(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.createGame();
        });
    }

    public void loginError(List<String> nicknames,String nickname ,String cause) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null) {
                connection.loginError(cause);
                removePlayer(nickname);
            }
        });
    }

    public void playerDisconnected(List<String> nicknames, String nickname) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.playerDisconnected(nickname);
        });
    }

    public void timerPing(List<String> nicknames, int timeLeft) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.timerPing(timeLeft);
        });
    }

    public void createGame(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.createGame();
        });
    }

    public void setSchemas(List<String> nicknames, List<String> schemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setSchemas(schemas);
        });
    }

    public void setPrivateCard(List<String> nicknames, String privateCard) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setPrivateCard(privateCard);
        });
    }

    public void setPublicObjectives(List<String> nicknames, List<String> publicObjectives) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setPublicObjectives(publicObjectives);
        });
    }

    public void setToolCards(List<String> nicknames, List<Integer> toolCards) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setToolCards(toolCards);
        });
    }

    public void chooseSchema(List<String> nicknames, String schema) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.chooseSchema(schema);
        });
    }

    public void setOpponentsSchemas(List<String> nicknames, List<String> opponentsSchemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setOpponentsSchemas(opponentsSchemas);
        });
    }

    public void schemaCustomAccepted(List<String> nicknames, String schema) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.schemaCustomAccepted(schema);
        });
    }

    public void setOpponentsCustomSchemas(List<String> nicknames, List<String> opponentsSchemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setOpponentsCustomSchemas(opponentsSchemas);
        });
    }

    public void startRound(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.startRound();
        });
    }

    public void startTurn(List<String> nicknames, String nickname) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.startTurn(nickname);
        });
    }

    public void setActions(List<String> nicknames, List<String> actions) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setActions(actions);
        });
    }

    public void setDiceSpace(List<String> nicknames, List<String> colours, List<Integer> values) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setDiceSpace(colours, values);
        });
    }

    public void draftDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.draftDiceAccepted();
        });
    }

    public void insertDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.insertDiceAccepted();
        });
    }

    public void moveDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.moveDiceAccepted();
        });
    }

    public void moveDiceError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.moveDiceError();
        });
    }

    public void pickDiceSpace(List<String> nicknames, Integer index) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceSpace(index);
        });
    }

    public void pickDiceSpaceError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceSpaceError();
        });
    }

    public void placeDiceSchema(List<String> nicknames, String nickname, int row, int column, String colour, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceSchema(nickname, row, column, colour, value);
        });
    }

    public void placeDiceSchemaError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceSchemaError();
        });
    }

    public void pickDiceSchema(List<String> nicknames, String nickname, int row, int column) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceSchema(nickname, row, column);
        });
    }

    public void pickDiceSchemaError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceSchemaError();
        });
    }

    public void useToolCardAccepted(List<String> nicknames, int favors) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.useToolCardAccepted(favors);
        });
    }

    public void useToolCardError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.useToolCardError();
        });
    }

    public void changeValueAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.changeValueAccepted();
        });
    }

    public void changeValueError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.changeValueError();
        });
    }

    public void placeDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceAccepted();
        });
    }

    public void rollDiceAccepted(List<String> nicknames, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.rollDiceAccepted(value);
        });
    }

    public void swapDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.swapDiceAccepted();
        });
    }

    public void pickDiceRoundTrack(List<String> nicknames, int nRound, int nDice) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceRoundTrack(nRound, nDice);
        });
    }

    public void pickDiceRoundTrackError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceRoundTrackError();
        });
    }

    public void placeDiceRoundTrack(List<String> nicknames, int nRound, List<String> colours, List<Integer> values) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceRoundTrack(nRound, colours, values);
        });
    }

    public void flipDiceAccepted(List<String> nicknames, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.flipDiceAccepted(value);
        });
    }

    public void cancelUseToolCardAccepted(List<String> nicknames, int favors) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.cancelUseToolCardAccepted(favors);
        });
    }

    public void placeDiceSpace(List<String> nicknames, String colour, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceSpace(colour, value);
        });
    }

    public void placeDiceSpaceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceSpaceAccepted();
        });
    }

    public void rollDiceSpaceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.rollDiceSpaceAccepted();
        });
    }

    public void swapDiceBagAccepted(List<String> nicknames, String colour, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.swapDiceBagAccepted(colour, value);
        });
    }

    public void chooseValueAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.chooseValueAccepted();
        });
    }

    public void chooseValueError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.chooseValueError();
        });
    }

    public void setWinner(List<String> nicknames, String nickname) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setWinner(nickname);
        });
    }

    public void setRankings(List<String> nicknames, List<String> players, List<Integer> scores) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setRankings(players, scores);
        });
    }

    public void setSchemasOnReconnect(List<String> nicknames, List<String> players, List<String> schemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setSchemasOnReconnect(players, schemas);
        });
    }

    public void setPublicObjectivesOnReconnect(List<String> nicknames, List<String> schemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setPublicObjectives(schemas);
        });
    }

    public void setToolCardsOnReconnect(List<String> nicknames, List<Integer> toolCards) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setToolCards(toolCards);
        });
    }

    public void setDiceSpaceOnReconnect(List<String> nicknames, List<String> colours, List<Integer> values) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setDiceSpace(colours, values);
        });
    }

    public void placeDiceRoundTrackOnReconnect(List<String> nicknames, int nRound, List<String> colours, List<Integer> values) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceRoundTrack(nRound, colours, values);
        });
    }

}
