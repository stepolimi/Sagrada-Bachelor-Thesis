package it.polimi.ingsw.Client.ClientConnection;

public interface Connection {

    public void sendMessage(String str);

    public void login(String nickname);

    public void disconnect();
}
