package it.polimi.ingsw.client.clientConnection;

public interface Connection {

    public void sendMessage(String str);

    public void login(String nickname);

    public void disconnect();
}
