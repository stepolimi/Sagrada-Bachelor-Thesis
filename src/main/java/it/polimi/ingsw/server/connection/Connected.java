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

    public void turnTimerPing(List<String> nicknames, int timeLeft) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.turnTimerPing(timeLeft);
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

    /**
     * Notify tht thre was an error while picking Dice from Schema
     * @param nicknames List of Players to send message to
     */
    public void pickDiceSchemaError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceSchemaError();
        });
    }

    /**
     * It notifies that player can use ToolCard
     * @param nicknames List of Players to send message to
     * @param favors number of the new favour
     */
    public void useToolCardAccepted(List<String> nicknames, int favors) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.useToolCardAccepted(favors);
        });
    }

    /**
     * It notifies that player can't use ToolCard
     * @param nicknames List of Players to send message to
     */
    public void useToolCardError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.useToolCardError();
        });
    }

    /**
     * It notifies that player has changed Dice value correctly
     * @param nicknames List of Players to send message to
     */
    public void changeValueAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.changeValueAccepted();
        });
    }

    /**
     * It notifies that player has changed Dice value wrongly
     * @param nicknames List of Players to send message to
     */
    public void changeValueError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.changeValueError();
        });
    }

    /**
     * It notifies that one player has placed Dice on schema
     * @param nicknames List of Players to send message to
     */
    public void placeDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceAccepted();
        });
    }

    /**
     * It notifies that one player has flipped a Dice picked before
     * @param nicknames List of Players to send message to
     * @param value New value of Dice flipped
     */
    public void rollDiceAccepted(List<String> nicknames, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.rollDiceAccepted(value);
        });
    }

    /**
     * It notifies that one player has flipped a Dice picked before
     * @param nicknames List of Players to send message to
     */
    public void swapDiceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.swapDiceAccepted();
        });
    }

    /**
     * it notify that one player has picked a Dice from a round in RoundTrack correctly
     * @param nicknames List of Players to send message to
     * @param nRound number of Round whre dice is picked
     * @param nDice index of dice picked in round
     */
    public void pickDiceRoundTrack(List<String> nicknames, int nRound, int nDice) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceRoundTrack(nRound, nDice);
        });
    }

    /**
     * it notify that one player has picked a Dice from a round in RoundTrack wrongly
     * @param nicknames List of Players to send message to
     */
    public void pickDiceRoundTrackError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.pickDiceRoundTrackError();
        });
    }

    /**
     * The method puts remaining dice from DiceSpace to Roundtrack current Round
     * @param nicknames nicknames of player
     * @param nRound round where dices are placed
     * @param colours colors of dice placed
     * @param values value of dices place on RoundTrack
     */
    public void placeDiceRoundTrack(List<String> nicknames, int nRound, List<String> colours, List<Integer> values) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceRoundTrack(nRound, colours, values);
        });
    }

    /**
     * it notify that one player has flipped a Dice from DiceSpace wrongly
     * @param nicknames List of Players to send message to
     * @param value value of Dice flipped
     */
    public void flipDiceAccepted(List<String> nicknames, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.flipDiceAccepted(value);
        });
    }

    /**
     * It notifies that ToolCard was not used
     * @param nicknames List of Players to send message to
     * @param favors
     */
    public void cancelUseToolCardAccepted(List<String> nicknames, int favors) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.cancelUseToolCardAccepted(favors);
        });
    }

    /**
     * It notifies to players the dice placed on DiceSpace
     * @param nicknames List of Players to send message to
     * @param colour color of dice
     * @param value value of dice
     */
    public void placeDiceSpace(List<String> nicknames, String colour, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceSpace(colour, value);
        });
    }

    /**
     * It notifies that a Dice was placed correctly
     * @param nicknames List of Players to send message to
     */
    public void placeDiceSpaceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceSpaceAccepted();
        });
    }

    /**
     * It notifies that player can roll the DiceSpace
     * @param nicknames List of Players to send message to
     */
    public void rollDiceSpaceAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.rollDiceSpaceAccepted();
        });
    }

    /**
     * It notifies correct SwapDiceBag Action
     * @param nicknames List of Players to send message to
     * @param colour color of dice
     * @param value value of dice
     */
    public void swapDiceBagAccepted(List<String> nicknames, String colour, int value) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.swapDiceBagAccepted(colour, value);
        });
    }

    /**
     * It notifies correct ChooseValue Action
     * @param nicknames List of Players to send message to
     */
    public void chooseValueAccepted(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.chooseValueAccepted();
        });
    }

    /**
     * It notifies wrong ChooseValue Action
     * @param nicknames List of Players to send message to
     */
    public void chooseValueError(List<String> nicknames) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.chooseValueError();
        });
    }

    /**
     * It notifies the winner of the match
     * @param nicknames List of Players to send message to
     * @param nickname name of player who win
     */
    public void setWinner(List<String> nicknames, String nickname) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setWinner(nickname);
        });
    }

    /**
     * It send score of every player sorted
     * @param nicknames List of Players to send message to
     * @param players List of player sorted by score
     * @param scores List of every @Players.get(i)
     */
    public void setRankings(List<String> nicknames, List<String> players, List<Integer> scores) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setRankings(players, scores);
        });
    }

    /**
     * It send every schema of every player when someone is reconnecting
     * @param nicknames List of Players to send message to
     * @param players List of Players who'splaying
     * @param schemas List of Schemas of every player
     */
    public void setSchemasOnReconnect(List<String> nicknames, List<String> players, List<String> schemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setSchemasOnReconnect(players, schemas);
        });
    }

    /**
     * The method set ObjectiveCards on a Player when is reconnected
     * @param nicknames List of Players to send message to
     * @param schemas publicObjective of the current game
     */
    public void setPublicObjectivesOnReconnect(List<String> nicknames, List<String> schemas) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setPublicObjectives(schemas);
        });
    }

    /**
     * The method set ToolCards on a Player when is reconnected
     * @param nicknames List of Players to send message to
     * @param toolCards Tools extracted
     */
    public void setToolCardsOnReconnect(List<String> nicknames, List<Integer> toolCards) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setToolCards(toolCards);
        });
    }

    /**
     * The method puts dice in DiceSpace of player when is reconnected
     * @param nicknames nicknames of players
     * @param colours colours of dice in DiceSpace
     * @param values  values of Dice in DiceSpace
     */
    public void setDiceSpaceOnReconnect(List<String> nicknames, List<String> colours, List<Integer> values) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.setDiceSpace(colours, values);
        });
    }

    /**
     * The method puts remaining dice from DiceSpace to Roundtrack current Round
     * @param nicknames nicknames of player
     * @param nRound round where dices are placed
     * @param colours colors of dice placed
     * @param values value of dices place on RoundTrack
     */
    public void placeDiceRoundTrackOnReconnect(List<String> nicknames, int nRound, List<String> colours, List<Integer> values) {
        nicknames.forEach(name -> {
            Connection connection = users.get(name);
            if (connection != null)
                connection.placeDiceRoundTrack(nRound, colours, values);
        });
    }

}
