package it.polimi.ingsw.client.clientConnection.rmi;


import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.setUp.TakeDataFile;
import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.client.view.Message;
import it.polimi.ingsw.client.view.TypeMessage;
import it.polimi.ingsw.server.connection.rmi.RmiServerMethodInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.client.constants.NameConstants.SERVER_IP;
import static it.polimi.ingsw.client.constants.NameConstants.RMI_PORT;
import static it.polimi.ingsw.client.constants.SetupConstants.CONFIGURATION_FILE;
import static it.polimi.ingsw.client.constants.printCostants.SERVER_CONNECTION_ERROR;
import static it.polimi.ingsw.client.constants.printCostants.THREAD_ERROR;
import static java.lang.Thread.sleep;

public class RmiConnection implements Connection {
    private RmiServerMethodInterface server;
    private RmiClientMethod client;
    private Handler hand;
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
        pingServer();
    }

        private void setConnection() throws IOException {
            TakeDataFile config = new TakeDataFile(CONFIGURATION_FILE);
            host = config.getParameter(SERVER_IP);
            port = Integer.parseInt(config.getParameter(RMI_PORT));
        }

        private void pingServer()
        {
            Thread t = new Thread(() -> {
                boolean  isRunning = true;
                while(isRunning) {
                    try {
                        server.ping();
                        sleep(5000);
                    } catch (RemoteException e) {
                        Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
                        isRunning = false;
                        System.exit(1);
                    } catch (InterruptedException ex){
                        Message.print(THREAD_ERROR, TypeMessage.ERROR_MESSAGE);
                        Thread.currentThread().interrupt();
                    }
                }
            });
            t.start();
        }
    public void sendSchema(final String str) {
        Thread t = new Thread(() -> {
            try {
                server.sendSchema(str, hand.getView().getName());
            } catch (RemoteException e) {
                Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
            }
        });
        t.start();

    }

    public void login(String nickname) {
        try {
            server.login(client, nickname);
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void disconnect() {
        Thread t = new Thread(()-> {
            try {
                server.disconnected(hand.getView().getName());
            } catch (RemoteException e) {
                Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
            }

        }); t.start();

    }

    public void insertDice(final int indexDiceSpace, final int row, final int column) {
        Thread t = new Thread(() -> {
            try {
                server.insertDice(hand.getView().getName(), indexDiceSpace, row, column);
            } catch (RemoteException e) {
                Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
            }
        });
        t.start();
    }

    public void useToolCard(int toolNumber) {
        try {
            server.useToolCard(hand.getView().getName(), toolNumber);
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void moveDice(final int oldRow, final int oldColumn, final int newRow, final int newColumn) {
        Thread t = new Thread(() -> {
            try {
                server.moveDice(hand.getView().getName(), oldRow, oldColumn, newRow, newColumn);
            } catch (RemoteException e) {
                Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
            }
        });
        t.start();

    }

    public void sendDraft(int indexDiceSpace) {
        try {
            server.draftDice(hand.getView().getName(), indexDiceSpace);
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void sendPlaceDice(int row, int column) {
        try {
            server.placeDice(hand.getView().getName(), row, column);
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void changeValue(String change) {
        try {
            server.changeValue(hand.getView().getName(), change);
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void rollDice() {
        try {
            server.rollDice(hand.getView().getName());
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void swapDice(int numRound, int indexDice) {
        try {
            server.swapDice(hand.getView().getName(), numRound, indexDice);
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void cancelUseToolCard() {
        try {
            server.cancelUseToolCard(hand.getView().getName());
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void flipDice() {
        try {
            server.flipDice(hand.getView().getName());
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void placeDiceSpace() {
        try {
            server.placeDiceSpace(hand.getView().getName());
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void rollDiceSpace() {
        try {
            server.rollDiceSpace(hand.getView().getName());
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void swapDiceBag() {
        try {
            server.swapDiceBag(hand.getView().getName());
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void chooseValue(int value) {
        try {
            server.chooseValue(hand.getView().getName(), value);
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void sendEndTurn() {
        try {
            server.sendEndTurn(hand.getView().getName());
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

    public void sendCustomSchema(String schema) {
        try {
            server.sendCustomSchema(schema, hand.getView().getName());
        } catch (RemoteException e) {
            Message.print(SERVER_CONNECTION_ERROR, TypeMessage.ERROR_MESSAGE);
        }
    }

}

