package it.polimi.ingsw.Client.ClientConnection;

public interface Connection {

    public void sendMessage(String str);

    public void login();

    public void disconnect();
}
