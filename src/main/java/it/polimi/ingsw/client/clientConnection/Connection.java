package it.polimi.ingsw.client.clientConnection;

public interface Connection {

    public void sendSchema(String str);

    public void login(String nickname);

    public void disconnect();
    public void insertDice(int indexDiceSpace,int row,int column);
}
