package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethodInterface;

import java.rmi.RemoteException;

public class Client implements Connection{
    RmiClientMethodInterface client;

    public Client(RmiClientMethodInterface client)
    {
        this.client = client;
    }


    public void sendMessage(String str) {
        try {
            client.printText(str);
        }catch(RemoteException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof Client) )
            return false;

        Client cli = (Client) obj;
        System.out.println("Ugualiiiiiii");
        System.out.println(this.client.equals(cli.client));
       return (this.client.equals(cli.client)) ;
    }
}
