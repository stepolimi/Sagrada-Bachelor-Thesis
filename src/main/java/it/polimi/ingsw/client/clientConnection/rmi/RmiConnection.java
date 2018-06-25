package it.polimi.ingsw.client.clientConnection.rmi;


import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.server.serverConnection.rmi.RmiServerMethodInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.client.constants.SetupConstants.RMI_SETUP_FILE;

public class RmiConnection implements Connection {
    private RmiServerMethodInterface server;
    private RmiClientMethod client;
    private Handler hand; // used to manage graphic
    private String host;
    private int port;

    public RmiConnection(Handler hand) throws RemoteException {
        this.hand = hand;
        try {
            setConnection();
            Registry registry = LocateRegistry.getRegistry(host, port);
            client = new RmiClientMethod(hand);
            server = (RmiServerMethodInterface) registry.lookup("RmiServerMethodInterface");
        } catch (NotBoundException e3) {
            System.out.println(e3.getMessage());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setConnection() throws IOException {
        FileReader f;
        f = new FileReader(RMI_SETUP_FILE);
        BufferedReader b;
        b = new BufferedReader(f);
        try {
            host = b.readLine();
            port = Integer.parseInt(b.readLine());
        } finally {
            b.close();
        }
    }

    public void sendSchema(final String str) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    server.sendSchema(str, hand.getView().getName());
                } catch (RemoteException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        t.start();

    }

    public void login(String nickname) {
        try {
            server.login(client, nickname);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect() {
        try {
            server.disconnected(client);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertDice(final int indexDiceSpace, final int row, final int column) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    server.insertDice(indexDiceSpace, row, column);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void useToolCard(int toolNumber) {
        try {
            server.useToolCard(toolNumber);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void moveDice(final int oldRow, final int oldColumn, final int newRow, final int newColumn) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    server.moveDice(oldRow, oldColumn, newRow, newColumn);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }

    public void sendDraft(int indexDiceSpace) {
        try {
            server.draftDice(indexDiceSpace);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendPlaceDice(int row, int column) {
        try {
            server.placeDice(row, column);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void changeValue(String change) {
        try {
            server.changeValue(change);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void rollDice() {
        try {
            server.rollDice();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void swapDice(int numRound, int indexDice) {
        try {
            server.swapDice(numRound, indexDice);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void cancelUseToolCard() {
        try {
            server.cancelUseToolCard();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void flipDice() {
        try {
            server.flipDice();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void placeDiceSpace() {
        try {
            server.placeDiceSpace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void rollDiceSpace() {
        try {
            server.rollDiceSpace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void swapDiceBag() {
        try {
            server.swapDiceBag();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void chooseValue(int value) {
        try {
            server.chooseValue(value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendEndTurn() {
        try {
            server.sendEndTurn();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendCustomSchema(String schema) {
        try {
            server.sendCustomSchema(schema, hand.getView().getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}

