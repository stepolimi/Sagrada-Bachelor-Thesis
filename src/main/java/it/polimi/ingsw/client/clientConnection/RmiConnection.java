package it.polimi.ingsw.client.clientConnection;


import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.server.serverConnection.RmiServerMethodInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RmiConnection implements Connection {
        RmiServerMethodInterface server;
        RmiClientMethod client;
        Handler hand; // used to manage graphic
        private String host;
        private int port;
        public RmiConnection(Handler hand)
        {
            this.hand= hand;
            try {
                try {
                    setConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Registry registry = LocateRegistry.getRegistry(host,port);
                client = new RmiClientMethod(hand);
                server = (RmiServerMethodInterface) registry.lookup("RmiServerMethodInterface");
            }catch(RemoteException e){
                System.out.println(e.getMessage());
            } catch(NotBoundException e3){
                System.out.println(e3.getMessage());
            }

        }
    public void setConnection() throws IOException
    {
        final String filePath = "src/main/resources/DataConnection/dataRmiConnection.txt";  //import every schema from
        //json file form /src/main/data/Schema/i.json
        FileReader f;
        f = new FileReader(filePath);
        int i=0;
        BufferedReader b;
        b = new BufferedReader(f);
        try {
            host = b.readLine();
            port = Integer.parseInt(b.readLine());
        }
        finally {
            b.close();
        }
    }

        public void sendSchema(String str) {
            try {
                List action = new ArrayList();
                action.add("ChooseSchema");
                action.add(hand.getView().getName());
                action.add(str);
                server.forwardAction(action,client);
            }catch(RemoteException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void login(String nickname) {
            try {
                server.login(client, nickname);
            }catch(RemoteException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void disconnect() {
            try {
                server.disconnected(client);
            }catch(RemoteException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void insertDice(final int indexDiceSpace, final int row, final int column) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        server.insertDice(indexDiceSpace,row,column);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }

        public void useToolCard(int toolNumber){
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
                        server.moveDice(oldRow,oldColumn,newRow,newColumn);
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
                server.placeDice(row,column);
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
                server.swapDice(numRound,indexDice);
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


}

