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

    /**
     * check if the username is already used
     * @param str is the name of tool card
     * @return false if is already use, true otherwise
     */
    public boolean checkUsername(String str) {
        if(users.keySet().contains(str)){
            Log.getLogger().addLog(INVALID_USERNAME , Level.INFO,this.getClass().getName(),CONNECTED_CHECK_USERNAME);
            return false;
        }
        return true;
    }

    /**
     * add player to game
     * @param user is name the player
     * @param connection is connection of player
     */
    public void addPlayer(String user,Connection connection){
        if(!users.keySet().contains(user))
            users.put(user,connection);
    }

    /**
     * remove player from the game
     * @param user is the name of player
     * @return true if player is removed, false otherwise
     */
    public boolean removePlayer(String user) {
        if(users.keySet().contains(user)) {
            users.remove(user);
            return true;
        }
        return false;
    }


    /**
     * log a player to game
     * @param nicknames list of players to send the message to
     * @param nickname is the nickname of the player wants to connect
     * @param lobbySize is the number of player in game
     */
    public void login(List<String> nicknames, String nickname, int lobbySize) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if(connection != null)
                connection.login(nickname, lobbySize);
        });
    }

    /**
     * reconnect a player
     * @param nicknames list of players to send the message to
     */
    public void reconnectPlayer(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.createGame();
        });
    }

    /**
     * send login error message
     * @param nicknames list of players to send the message to
     * @param nickname is the name of player
     * @param cause it is the cause of the disconnection of the player
     */
    public void loginError(List<String> nicknames,String nickname ,String cause) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null) {
                connection.loginError(cause);
                removePlayer(nickname);
            }
        });
    }

    /**
     * disconnect a player
     * @param nicknames list of players to send the message to
     * @param nickname is the name of player
     */
    public void playerDisconnected(List<String> nicknames, String nickname) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.playerDisconnected(nickname);
        });
    }

    /**
     * send lobby timer
     * @param nicknames list of players to send the message to
     * @param timeLeft is remaining time
     */
    public void timerPing(List<String> nicknames, int timeLeft) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.timerPing(timeLeft);
        });
    }

    /**
     * send  turn timer
     * @param nicknames list of players to send the message to
     * @param timeLeft is remaining time
     */
    public void turnTimerPing(List<String> nicknames, int timeLeft) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.turnTimerPing(timeLeft);
        });
    }

    /**
     * create game
     * @param nicknames list of players to send the message to
     */
    public void createGame(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.createGame();
        });
    }

    /**
     * set schemas
     * @param nicknames list of players to send the message to
     * @param schemas is the name of player's scheme
     */
    public void setSchemas(List<String> nicknames, List<String> schemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setSchemas(schemas);
        });
    }

    /**
     * set private card to game
     * @param nicknames list of players to send the message to
     * @param privateCard is the name of private card
     */
    public void setPrivateCard(List<String> nicknames, String privateCard) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setPrivateCard(privateCard);
        });
    }

    /**
     * set public objective
     * @param nicknames list of players to send the message to
     * @param publicObjectives is tha name of public objective
     */
    public void setPublicObjectives(List<String> nicknames, List<String> publicObjectives) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setPublicObjectives(publicObjectives);
        });
    }

    /**
     * set tool card
     * @param nicknames list of players to send the message to
     * @param toolCards is the name of tool card
     */
    public void setToolCards(List<String> nicknames, List<Integer> toolCards) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setToolCards(toolCards);
        });
    }

    /**
     * set schema player
     * @param nicknames list of players to send the message to
     * @param schema is the name of schema
     */
    public void chooseSchema(List<String> nicknames, String schema) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.chooseSchema(schema);
        });
    }

    /**
     * set opponents schema
     * @param nicknames list of players to send the message to
     * @param opponentsSchemas is the name of opponents schemas
     */
    public void setOpponentsSchemas(List<String> nicknames, List<String> opponentsSchemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setOpponentsSchemas(opponentsSchemas);
        });
    }

    /**
     * accept costum schema
     * @param nicknames list of players to send the message to
     * @param schema is the name of scheme accepted
     */
    public void schemaCustomAccepted(List<String> nicknames, String schema) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.schemaCustomAccepted(schema);
        });
    }

    /**
     * set opponents custom schema
     * @param nicknames list of players to send the message to
     * @param opponentsSchemas is the name of opponents schemas
     */
    public void setOpponentsCustomSchemas(List<String> nicknames, List<String> opponentsSchemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setOpponentsCustomSchemas(opponentsSchemas);
        });
    }

    /**
     * start round
     * @param nicknames list of players to send the message to
     */
    public void startRound(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.startRound();
        });
    }

    /**
     * start turn
     * @param nicknames list of players to send the message to
     * @param nickname is the name of the player to whom the turn was assigned
     */
    public void startTurn(List<String> nicknames, String nickname) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.startTurn(nickname);
        });
    }

    /**
     * set possible action
     * @param nicknames list of players to send the message to
     * @param actions
     */
    public void setActions(List<String> nicknames, List<String> actions) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setActions(actions);
        });
    }

    /**
     * set dice space
     * @param nicknames list of players to send the message to
     * @param colours is the colour of dice
     * @param values is the value of dice
     */
    public void setDiceSpace(List<String> nicknames, List<String> colours, List<Integer> values) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setDiceSpace(colours, values);
        });
    }

    /**
     * send draft dice accepted message
     * @param nicknames list of players to send the message to
     */
    public void draftDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.draftDiceAccepted();
        });
    }

    /**
     * send insert dice message accept
     * @param nicknames list of players to send the message to
     */
    public void insertDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.insertDiceAccepted();
        });
    }

    /**
     * send move dice message accept
     * @param nicknames list of players to send the message to
     */
    public void moveDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.moveDiceAccepted();
        });
    }

    /**
     * send move dice error message
     * @param nicknames list of players to send the message to
     */
    public void moveDiceError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.moveDiceError();
        });
    }

    /**
     * pick dice from dice space
     * @param nicknames list of players to send the message to
     * @param index is the index of dice space
     */
    public void pickDiceSpace(List<String> nicknames, Integer index) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceSpace(index);
        });
    }

    /**
     * send pick dice space error
     * @param nicknames list of players to send the message to
     */
    public void pickDiceSpaceError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceSpaceError();
        });
    }

    /**
     * place dice in schema
     * @param nicknames list of players to send the message to
     * @param nickname is the name of player
     * @param row is the index of row
     * @param column is the index of columns
     * @param colour is the colour of dice
     * @param value is the value of dice
     */
    public void placeDiceSchema(List<String> nicknames, String nickname, int row, int column, String colour, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceSchema(nickname, row, column, colour, value);
        });
    }

    /**
     * send place dice schema error message
     * @param nicknames list of players to send the message to
     */
    public void placeDiceSchemaError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceSchemaError();
        });
    }

    /**
     * pick dice from schema
     * @param nicknames list of players to send the message to
     * @param nickname is the name of player
     * @param row is index of row
     * @param column is index of column
     */
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
