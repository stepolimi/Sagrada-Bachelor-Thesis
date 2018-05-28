package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.costants.GameCreationMessages.endTurn;
import static it.polimi.ingsw.costants.TimerCostants.LobbyTimerValue;


public class ViewCLI implements View{
    private Scanner input;
    private String username="";
    private Connection connection;
    private Handler hand;
    private HashMap<String,Schema> schemas ;

    private ArrayList <String> moves;
    private String privateObjective;
    private List <String> publicObjcective ;
    private List <String> toolCard;
    private List <Dices> diceSpace;
    private boolean correct;
    private boolean myTurn=false;
    private int row;
    private int column;
    private int indexDiceSpace;
    private int nPlayer;
    private static final String operatingSystem = System.getProperty("os.name");
    public ViewCLI()
    {
        moves = new ArrayList<String>();
        input = new Scanner(System.in);
        schemas = new HashMap<String, Schema>();
        privateObjective = "";
        publicObjcective = new ArrayList<String>();
        toolCard = new ArrayList<String>();
        diceSpace = new ArrayList<Dices>();
    }





// set method
    public void setScene(String scene) {
        if(scene.equals("connection"))this.setConnection();
        else if(scene.equals("login"))this.setLogin();
    }

    public void setConnection()
    {
         correct= false;
        String choose;
        while(!correct) {
            System.out.println("Scegli la tua connessione");
            System.out.println("1 ----> Socket");
            System.out.println("2 ----> Rmi");
            choose = input.nextLine();
            if (choose.equals("1"))
            {
                try {
                    connection = new SocketConnection(hand);
                    Thread t = new Thread((SocketConnection)connection);
                    t.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                correct = true;
            }
            else if (choose.equals("2"))
            {
                connection = new RmiConnection(hand);
                correct = true;
            }
            else
                correct = false;
        }
    }

    public void setLogin()
    {
        username = "";
        while(username.equals("")) {
            System.out.println("Inserisci il tuo username:");
            username = input.nextLine();
        }
        connection.login(username);

    }

    public void setSchemas(List<String> schemas){
        System.out.println("scrivi il nome dello schema che preferisci tra:");
        for(String s: schemas)
            showSchemas(s);

        Thread t = new Thread(new Runnable() {
            public void run() {
                String nameSchema;
                nameSchema = input.nextLine();
                connection.sendSchema(nameSchema);
            }
        });
        t.start();
        System.out.println("\n");
    }

    public void setDiceSpace(List<String> dice)
    {
        System.out.println("setto la diceSpace");
        for(int i=0;i<dice.size();i=i+2)
        {
            diceSpace.add(new Dices("",Integer.parseInt(dice.get(i+1)), Colour.stringToColour(dice.get(i))));
        }
    }

    public void insertDiceAccepted(List action) {
        this.schemas.get(username).getGrid()[row][column]= diceSpace.get(indexDiceSpace);
    }

    public void pickDiceSpace(List action) {
        diceSpace.remove(indexDiceSpace);
    }

    public void pickDiceSpaceError(List action) {
        System.out.println("posizione di toglle del dado dalla dicespace completamente sbagliata");
        //todo ristampare le azioni;
     }

    public void placeDiceSchema(List action) {
        if(!action.get(0).equals(username)){
            this.schemas.get(action.get(0)).getGrid()[(Integer)action.get(1)][(Integer)action.get(2)]=
                    new Dices("",(Integer)action.get(4),Colour.stringToColour((String)action.get(3)));
        }
    }

    public void placeDiceSchemaError(List action) {
        System.out.println("errore nell'inserimento del dado");
        //todo ristampare le azioni;
    }



    public void setPrivateCard(String colour){
        System.out.println("il tuo obiettivo privato sarà il colore: " + colour + "\n");
        privateObjective = colour;
    }

    public void setPublicObjectives(List<String> cards){
        System.out.println("gli obiettivi publici per questa partita saranno:");
        for(String s: cards)
            System.out.println(s);
        publicObjcective = cards;
        System.out.println("\n");
    }

    public void setToolCards(List<String> cards){
        System.out.println("le carte utensili per questa partita saranno:");
        for(String s: cards)
            System.out.println("la carta numero " + s + ",");
        toolCard = cards;
        System.out.println("\n");
    }

    public void setHandler(Handler hand) {
        this.hand = hand;
    }

    public void setOpponentsSchemas(List <String> s)
    {
        Schema temp= new Schema();
        for(int i=0;i<s.size();i=i+2) {
            try {
                if(!s.get(i).equals(this.getName()))
                    schemas.put(s.get(i),temp.InitSchema(s.get(i+1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showOpponentsSchemas();
    }

    public void setNumberPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    public void startRound() {
        System.out.println("nuovo round iniziato");

    }

    public void setActions(List<String> actions) {
        moves.clear();
        moves.add("Mostra l'obiettivo privato");
        moves.add("Mostra gli obiettivi pubblici");
        moves.add("Mostra schemi avversari");
        moves.add("Mostra le tool card");
        if(actions == null)
            return;
        for(String action: actions)
            moves.add(action);
        showMoves();
    }

    // show method
    public void showSchemas(String nome)
    {
        Schema schema = new Schema();
        try {
            System.out.println(schema.InitSchema(nome).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showDiceSpace()
    {
        for(Dices d:diceSpace)
        {
            System.out.print(d.toString());
        }
        System.out.println("\n");
    }

    public void showOpponentsSchemas()
    {
        for(String key:schemas.keySet())
            if(!key.equals(username))
                System.out.println(schemas.get(key).toString());
    }
    public void showPrivateObjective()
    {
        System.out.println("Il tuo obiettivo privato è:"+privateObjective);
    }

    public void showPublicObjective()
    {
        System.out.println("Gli obiettivi pubblici della partita sono:");
        for(String pub: publicObjcective)
            System.out.println(pub);
    }

    public void showToolCard()
    {
        System.out.println("Le toolcard sono:");
        for(String tool:toolCard)
            System.out.println(tool);
    }




    public void showMoves()
    {
        System.out.println("Scegli una tra le seguenti opzioni:");
        for(int i=0;i<moves.size();i++)
            System.out.println((i+1)+")"+moves.get(i));
    }


    // action method

    public void startScene() {
        System.out.println("\u001B[32m" + "W E L C O M E ");
        System.out.println("\u001B[0m"+"You can choose a type of:");
        System.out.println("Graphic Interface: GUI or CLI");
        System.out.println("Connection: RMI/SOCKET");
    }

    public void login(String str) {
        if (str.equals("Welcome"))
        {
            System.out.println("La partita inizierà a breve");
            System.out.println("Aspettando altri giocatori...");
        }else if(str.equals("Login_error-username")) {
            System.out.println("Errore, nickname già in uso");
            this.setLogin();
        }else if(str.equals("Login_error-game")) {
            System.out.println("Errore, partita già in corso");
            System.out.println("Riprovare più tardi");

        }
    }

    public void playerConnected(String name){
        System.out.print("\r");
        System.out.println(name + " si è aggiunto alla lobby\n");
        nPlayer++;
    }

    public void playerDisconnected(String name){
        System.out.print("\r");
        System.out.println(name + " si è disconnesso\n");
        nPlayer--;
    }

    public void timerPing(String time) {
        //System.out.println("Caricamento");
       // for(int i=0;i<(LobbyTimerValue-Integer.parseInt(time))/5;i++) {
            int percent =(int)(((LobbyTimerValue-Double.parseDouble(time))/LobbyTimerValue)*100);
            System.out.print("\u001B[34m\rLoading:");
            for(int i=0;i<percent;i++)
            {
                System.out.print("▋");
            }
            for(int i=percent;i<100;i++)
            {
                System.out.print(" ");
            }
        System.out.print(percent+"%\u001B[0m");
       // }
       // System.out.println("la partita inizierà tra " + time + " secondi\n");
    }

    public void createGame(){
        System.out.println("\npartita creata\n");
    }




    public void chooseSchema(String name)
    {
        try {
            Schema s = new Schema();
            schemas.put(this.getName(),s.InitSchema(name));
            System.out.println("schema approvato: " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startTurn(String name)
    {
        System.out.println("Il tuo schema:");
        System.out.println(schemas.get(username).toString());
        showDiceSpace();
        if(!name.equals(username)) {
            myTurn = false;
            System.out.println("turno iniziato, tocca a: " + name);
            setActions(null);
            showMoves();
        }
        else
            myTurn = true;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while(myTurn)
                    chooseMoves();
            }
        });
        thread.start();
    }

    public void chooseMoves()
    {
        String move;
        int choose;

        //to be modified later
        move = input.nextLine();
        choose = Integer.parseInt(move);
        if(moves.get(choose-1).equals("Inserisci un dado"))
        {
            insertDice();

            // se ricevo una risposta positiva dal server allora tolgo dalle azioni la possibilità di inserire un dado
            //moves.remove(choose-1);
        }else if(moves.get(choose-1).equals("Usa una toolcard"))
        {
            useToolCard();
            // se ricevo una risposta positiva dal server allora tolgo dalle azioni la possibilità di utilizzare la toolcard
            // moves.remove(choose-1);
        }

        switch(choose-1)
        {
            case 0:showPrivateObjective(); break;
            case 1: showPublicObjective();break;
            case 2: showOpponentsSchemas();break;
            case 3: showToolCard();break;
        }

    }



    public void passTurn()
    {
        this.myTurn = false;
        //connection.sendMessage(endTurn);
    }

    public void insertDice()
    {
        // devo controllare la diceSpace
        correct = false;
        while(!correct) {
            try {
                System.out.println("Inserisci l'indice del dado della riserva:(Da 1 a "+diceSpace.size()+")");
                indexDiceSpace = Integer.parseInt(input.nextLine());

                if(indexDiceSpace<=0 || indexDiceSpace>diceSpace.size())
                    throw new NumberFormatException();

                System.out.println("Inserisci la riga");
                row = Integer.parseInt(input.nextLine());

                if(row<=0 || row>4)
                    throw  new NumberFormatException();

                System.out.println("Inserisci la oolonna");
                column = Integer.parseInt(input.nextLine());

                if (column<=0 || column>5)
                    throw  new NumberFormatException();

                correct = true;
                connection.insertDice(indexDiceSpace-1,row,column);

            }catch(NumberFormatException e) {
                System.out.println("Formato non valido");
            }/*catch(IndexException ex)
            {
                System.out.println("Indice errato");
            }
           */
        }

    }



    public void useToolCard()
    {
        correct = false;
        System.out.println("Scegli la tool card da utilizzare:");
        for(String s:toolCard)
            System.out.println(s);
            String tool = input.nextLine();
      //  connection.sendMessage(tool);
        // connection.useToolCard()
    }



    public String getName(){ return this.username;}
}

