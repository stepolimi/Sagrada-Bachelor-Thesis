package it.polimi.ingsw.server.serverConnection;

import java.util.*;

public class Connected {
private HashMap <Connection,String> users = new HashMap<Connection,String>();

    public Map<Connection,String> getUsers() {
        return users;
    }

    private Connection getPlayerConnection(String player){
        Iterator <Connection> it = users.keySet().iterator();
        while(it.hasNext())
        {
            Connection conn = it.next();
            if(users.get(conn).equals(player)) {
                return conn;
            }
        }
        return null;
    }

    public int nConnection()
    {
        return users.size();
    }

    public boolean checkUsername(String str) {
        Iterator <Connection> it = users.keySet().iterator();
        while(it.hasNext())
        {
            Connection conn = it.next();
            if(users.get(conn).equals(str))
            {
                System.out.println("connection failed: invalid username\n" + " ---");
                return false;
            }
        }

        return true;
    }

    public String remove(Connection user)
    {
        String name=null;
        Iterator <Connection> it = users.keySet().iterator();
        while(it.hasNext())
        {
            Connection conn = it.next();
            if(user.equals(conn))
            {
                name = users.get(conn);
                it.remove();
            }
        }

        return name;
    }


    public void login(String nickname, int lobbySize) {
        users.forEach((connection,name) -> connection.login(nickname,lobbySize));
    }

    public void loginError(String nickname, String cause) {
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null) {
            connection.loginError(cause);
            remove(connection);
        }
    }

    public void playerDisconnected(String nickname){
        users.forEach((connection,name) -> connection.playerDisconnected(nickname));
    }

    public void timerPing(int timeLeft){
        users.forEach((connection,name) -> connection.timerPing(timeLeft));
    }

    public void createGame() {
        users.forEach((connection,name) -> connection.createGame());
    }

    public void setSchemas(String nickname, List<String> schemas) {
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.setSchemas(schemas);
    }

    public void setPrivateCard(String nickname, String privateCard){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.setPrivateCard(privateCard);
    }

    public void setPublicObjectives(List<String> publicObjectives){
        users.forEach((connection,name) -> connection.setPublicObjectives(publicObjectives));
    }

    public void setToolCards(List<Integer> toolCards) {
        users.forEach((connection,name) -> connection.setToolCards(toolCards));
    }

    public void chooseSchema(String nickname, String schema){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.chooseSchema(schema);
    }

    public void setOpponentsSchemas(List<String> opponentsSchemas){
        users.forEach((connection,name) -> connection.setOpponentsSchemas(opponentsSchemas));
    }

    public void schemaCustomAccepted(String nickname, String schema){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.schemaCustomAccepted(schema);
    }

    public void setOpponentsCustomSchemas(List<String> opponentsSchemas){
        users.forEach((connection,name) -> connection.setOpponentsCustomSchemas(opponentsSchemas));
    }

    public void startRound() {
        users.forEach((connection,name) -> connection.startRound());
    }

    public void startTurn(String nickname){
        users.forEach((connection,name) -> connection.startTurn(nickname));
    }

    public void setActions(String nickname, List<String> actions){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.setActions(actions);
    }

    public void setDiceSpace(List<String> colours, List<Integer> values){
        users.forEach((connection,name) -> connection.setDiceSpace(colours,values));
    }

    public void draftDiceAccepted(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.draftDiceAccepted();
    }

    public void insertDiceAccepted(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.insertDiceAccepted();
    }

    public void moveDiceAccepted(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.moveDiceAccepted();
    }

    public void pickDiceSpace(Integer index){
        users.forEach((connection,name) -> connection.pickDiceSpace(index));
    }

    public void pickDiceSpaceError(String nickname) {
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.pickDiceSpaceError();
    }

    public void placeDiceSchema(String nickname,int row,int column,String colour,int value){
        users.forEach((connection,name) -> connection.placeDiceSchema(nickname,row,column,colour,value));
    }

    public void placeDiceSchemaError(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.placeDiceSchemaError();
    }

    public void pickDiceSchema(String nickname, int row, int column){
        users.forEach((connection,name) -> connection.pickDiceSchema(nickname,row,column));
    }

    public void pickDiceSchemaError(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.pickDiceSchemaError();
    }

    public void useToolCardAccepted(String nickname,int favors) {
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.useToolCardAccepted(favors);
    }

    public void useToolCardError(String nickname) {
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.useToolCardError();
    }

    public void changeValueAccepted(String nickname) {
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.changeValueAccepted();
    }

    public void changeValueError(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.changeValueError();
    }

    public void placeDiceAccepted(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.placeDiceAccepted();
    }

    public void rollDiceAccepted(String nickname, int value){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.rollDiceAccepted(value);
    }

    public void swapDiceAccepted(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.swapDiceAccepted();
    }

    public void pickDiceRoundTrack(int nRound, int nDice ){
        users.forEach((connection,name) -> connection.pickDiceRoundTrack(nRound,nDice));
    }

    public void pickDiceRoundTrackError(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.pickDiceRoundTrackError();
    }

    public void placeDiceRoundTrack(int nRound, List<String> colours, List<Integer> values) {
        users.forEach((connection,name) -> connection.placeDiceRoundTrack(nRound,colours,values));
    }

    public void flipDiceAccepted(String nickname, int value){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.flipDiceAccepted(value);
    }

    public void cancelUseToolCardAccepted(String nickname, int favors) {
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.cancelUseToolCardAccepted(favors);
    }

    public void placeDiceSpace(String colour, int value){
        users.forEach((connection,name) -> connection.placeDiceSpace(colour,value));
    }

    public void placeDiceSpaceAccepted(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.placeDiceSpaceAccepted();
    }

    public void rollDiceSpaceAccepted(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.rollDiceSpaceAccepted();
    }

    public void swapDiceBagAccepted(String nickname,String colour, int value){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.swapDiceBagAccepted(colour,value);
    }

    public void chooseValueAccepted(String nickname){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.chooseValueAccepted();
    }

    public void chooseValueError(String nickname) {
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.chooseValueError();
    }

    public void setWinner(String nickname) {
        users.forEach((connection,name) -> connection.setWinner(nickname));
    }

    public void setRankings(List<String> players, List<Integer> scores) {
        users.forEach((connection,name) -> connection.setRankings(players,scores));
    }

    public void setSchemasOnReconnect(String nickname, List<String> players, List<String> schemas){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.setSchemasOnReconnect(players,schemas);
    }

    public void setPublicObjectivesOnReconnect(String nickname, List<String> schemas){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.setPublicObjectives(schemas);
    }

    public void setToolCardsOnReconnect(String nickname, List<Integer> toolCards){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.setToolCards(toolCards);
    }

    public void setDiceSpaceOnReconnect(String nickname, List<String> colours, List<Integer> values){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.setDiceSpace(colours,values);
    }

    public void placeDiceRoundTrackOnReconnect(String nickname, int nRound, List<String> colours, List<Integer> values){
        Connection connection = getPlayerConnection(nickname);
        if(connection!= null)
            connection.placeDiceRoundTrack(nRound,colours,values);
    }

}
