package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;
import it.polimi.ingsw.server.exception.WrongInputException;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.costants.GameConstants.PAINT_ROW;
import static it.polimi.ingsw.costants.TimerCostants.LOBBY_TIMER_VALUE;


public class ViewCLI implements View{
    private Scanner input;
    private String username;
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
    private int round;
    private boolean gameRunning;
    private Thread schemaThread ;
    private Dices pendingDice;
    private int opVal;
    private LoadImage load;
    private int oldRow;
    private int oldColumn;
    private int newRow;
    private int newColumn;
    public ViewCLI()
    {
        load = new LoadImage();
        opVal = 0;
        round = 0;
        username = "";
        gameRunning = true;
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
    // used to connect with server
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
                    correct = true;
                } catch (IOException e) {
                    System.out.println(Colour.colorString("Errore di connessione con il server, riprovare",Colour.ANSI_RED));
                    correct = false;
                }
            }
            else if (choose.equals("2"))
            {
                try {
                    connection = new RmiConnection(hand);
                    correct = true;
                } catch (RemoteException e) {
                    System.out.println(Colour.colorString("Errore di connessione con il server, riprovare",Colour.ANSI_RED));
                    correct = false;
                }

            }
            else
                correct = false;
        }
        this.startScene();
    }
    // used to login with server
    public void setLogin()
    {
        username = "";
        while(username.equals("")) {
            System.out.println("Inserisci il tuo username:");
            username = input.nextLine();
        }
        connection.login(username);

    }
    // used to choose the own schema
    public void setSchemas(List<String> schemas){
        System.out.println("Scrivi il nome dello schema che preferisci tra:");
        HashMap<String,Schema> selSchema = new HashMap<String, Schema>();
        for(String nameSchema:schemas)
        {
            try {
                selSchema.put(nameSchema,new Schema().InitSchema("SchemaClient/"+nameSchema));
                selSchema.get(nameSchema).splitImageSchema();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            showSchemas(selSchema);


        schemaThread = new Thread(new Runnable() {
            public void run() {
                try {
                    String nameSchema;
                    nameSchema = input.nextLine();
                    if(!schemaThread.isInterrupted())
                    connection.sendSchema(nameSchema);
                }catch(Exception e)
                {
                    System.out.println("Schema già inserito");
                }
            }
        });
        schemaThread.start();
        System.out.println("\n");
    }

    // used to set round's diceSpace
    public void setDiceSpace(List<String> dice)
    {
        diceSpace.clear();
        for(int i=0;i<dice.size();i=i+2)
        {
            diceSpace.add(new Dices("",Integer.parseInt(dice.get(i+1)), Colour.stringToColour(dice.get(i))));
        }
    }

    // invoked by server to accept InsertDice action
    public void insertDiceAccepted() {
        diceSpace.get(indexDiceSpace).setConstraint(this.schemas.get(username).getGrid()[row][column].getConstraint());
        this.schemas.get(username).getGrid()[row][column]= diceSpace.get(indexDiceSpace);
        schemas.get(username).splitImageSchema();
        schemas.get(username).showImage();
    }

    // used to remove Dice from DiceSpace
    public void pickDiceSpace(List action) {
        diceSpace.remove(Integer.parseInt((String)action.get(0)));
    }

    // used to notify the user of an insertDiceSpace error
    public void pickDiceSpaceError() {
        System.out.println("Indice della riserva non corretto");
     }

     // used to place a Dice in Schema
    public void placeDiceSchema(List action) {
        if(!action.get(0).equals(username)){
            this.schemas.get(action.get(0)).getGrid()[Integer.parseInt((String)action.get(1))][Integer.parseInt((String)action.get(2))]=
                    new Dices("",Integer.parseInt((String) action.get(4)),Colour.stringToColour((String)action.get(3)));
        }
    }

    // used to notify the user of an placeDiceSchema error
    public void placeDiceSchemaError() {
        System.out.println(Colour.colorString("Errore nell'inserimento del dado",Colour.ANSI_RED));
    }

    // set private objective card
    public void setPrivateCard(String colour){
        System.out.println("il tuo obiettivo privato sarà il colore: " + colour + "\n");
        privateObjective = colour;
    }

    // set public objective card used in the game
    public void setPublicObjectives(List<String> cards){
       /* System.out.println("gli obiettivi publici per questa partita saranno:");
        for(String s: cards)
            System.out.println(s);
       */
        publicObjcective = cards;
        System.out.println("\n");
    }

    // set tool cards used in the game
    public void setToolCards(List<String> cards){
        toolCard = cards;
        System.out.println("\n");
    }


    public void setHandler(Handler hand) {
        this.hand = hand;
    }

    // create your custom schema
    public void createSchema() {
        int nCostraint=0;
        System.out.println(Colour.colorString(" Hai scelto di creare la tua griglia: ",Colour.ANSI_BLUE));
        Schema sc = new Schema();
        System.out.println("Scegli il nome della griglia:");
        sc.setName(input.nextLine());

        for (int i = 0; i < sc.getGrid().length; i++)
        {
            for (int j = 0; j < sc.getGrid()[0].length; j++) {
                System.out.println("Inserisci la restrizione della cella:" + "[" + i + "][" + j + "]");
                if(setConstraint(sc,i,j))
                    nCostraint++;
            }
        }
        setDifficult(sc,nCostraint);
        System.out.println("La griglia che hai creato è questa:");
        System.out.println(sc);
        System.out.println("Desideri apportare modifiche? y si");
        if(input.nextLine().equals("y"))
            modifySchema(sc);
        System.out.println("Vuoi salvare su file il tuo schema? y si");
        try {
            saveSchema(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        schemas.put(username,sc);
    }

    // set constraint of custom scheme
    public boolean setConstraint(Schema sc, int i, int j)
    {
        boolean isConstraint=true;
        String constraint;
        correct = false;
        while (!correct) {
            correct = true;
            System.out.println("1) colore "+Colour.ANSI_GREEN.escape()+"verde(g)-"+Colour.ANSI_RED.escape()+"rosso(r)-"+Colour.ANSI_BLUE.escape()+"blu(b)-"+Colour.ANSI_YELLOW.escape()+"giallo(y)"+Colour.ANSI_PURPLE.escape()+"-viola(p)"+Colour.RESET);
            System.out.println("2) numero 1-6");
            System.out.println("3) nessuna restrizione (e)");
            constraint = input.nextLine();
            char word = constraint.charAt(0);
            switch (word) {
                case 'r':
                    sc.getGrid()[i][j].setConstraint(Colour.ANSI_RED.escape());
                    break;
                case 'g':
                    sc.getGrid()[i][j].setConstraint(Colour.ANSI_GREEN.escape());
                    break;
                case 'y':
                    sc.getGrid()[i][j].setConstraint(Colour.ANSI_YELLOW.escape());
                    break;
                case 'b':
                    sc.getGrid()[i][j].setConstraint(Colour.ANSI_BLUE.escape());
                    break;
                case 'p':
                    sc.getGrid()[i][j].setConstraint(Colour.ANSI_PURPLE.escape());
                    break;
                case '1':
                    sc.getGrid()[i][j].setConstraint("1");
                    break;
                case '2':
                    sc.getGrid()[i][j].setConstraint("2");
                    break;
                case '3':
                    sc.getGrid()[i][j].setConstraint("3");
                    break;
                case '4':
                    sc.getGrid()[i][j].setConstraint("4");
                    break;
                case '5':
                    sc.getGrid()[i][j].setConstraint("5");
                    break;
                case '6':
                    sc.getGrid()[i][j].setConstraint("6");
                    break;
                case 'e': {
                    sc.getGrid()[i][j].setConstraint("");
                    isConstraint = false;
                }
                    break;
                default:
                    correct = false;

            }
        if(!sc.nearConstraint(i,j,sc.getGrid()[i][j].getConstraint()))
        {
            correct = false;
            System.out.println(Colour.ANSI_RED.escape()+" Restrizione già immessa nelle caselle adiacenti "+Colour.RESET);
            System.out.println("Selezionarne un'altra");
        }
        }

        return isConstraint;
    }

    // calculate difficult of custom scheme
    public void setDifficult(Schema sc,int nConstraint)
    {
        int difficult;

        if(nConstraint<8)
            difficult = 1;
        else if(nConstraint<11)
            difficult = 2;
        else if(nConstraint<12)
            difficult=3;
        else if(nConstraint<13)
            difficult=4;
        else if(nConstraint<14)
            difficult=5;
        else
            difficult=6;

        sc.setDifficult(difficult);
    }

    // used to modify custom schema
    public void modifySchema(Schema s)
    {
        int row, column;
        correct = false;
        while(!correct) {


            try {
                System.out.println("Inserisci la riga che vuoi modificare");
                row = Integer.parseInt(input.nextLine());

                System.out.println("Inserisci la colonna che vuoi modificare");
                column = Integer.parseInt(input.nextLine());

                setConstraint(s,row,column);

                System.out.println("Modifica avvenuta!");

                System.out.println("Questo è il tuo schema");
                System.out.println(s);

                System.out.println("Vuoi modificare ancora la griglia? y si");

                if (input.nextLine().equals("y"))
                    correct = false;
                else
                    correct = true;

            } catch (NumberFormatException e) {
                correct = false;
            }
        }
    }

    // used to save custom schema
    public void saveSchema(Schema s) throws IOException
    {
        String path = "src/main/data/SchemaPlayer/";
        String name,schema;
        Gson g = new Gson();
        schema = g.toJson(s);
        correct = false;
        while(!correct)
        {
            String copyPath;
            System.out.println("Inserisci il nome che vorrai dare allo schema:");
            name = input.nextLine();
            copyPath = path + name + ".json";
            FileWriter fw=null;
            BufferedWriter b;
                File file = new File(copyPath);

                if (file.exists())
                    System.out.println("Il file " + copyPath + " esiste già");
                else if (file.createNewFile())
                {
                    System.out.println("Il file " + copyPath + " è stato creato");
                    fw = new FileWriter(file);
                    b = new BufferedWriter(fw);
                    b.write(schema);
                    b.flush();
                    fw.close();
                    b.close();
                    correct = true;
                }
                else
                    System.out.println("Il file " + path + " non può essere creato");

        }

    }

    // used to set opponents schemas
    public void setOpponentsSchemas(List <String> s)
    {
        clearScreen();
        Schema temp= new Schema();

        for(int i=0;i<s.size();i=i+2) {
            try {
                if(!s.get(i).equals(this.getName()))
                    schemas.put(s.get(i),temp.InitSchema("SchemaClient/"+s.get(i+1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // used to set the number of player in the game
    public void setNumberPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    // invoked when start the rounds
    public void startRound() {
        round++;
        if(round == 1) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    while (gameRunning)
                        chooseMoves();
                }
            });
            thread.start();
        }
    }

    // used to set legal action
    public void setActions(List<String> actions) {
        moves.clear();
        moves.add("Mostra l'obiettivo privato");
        moves.add("Mostra gli obiettivi pubblici");
        moves.add("Mostra schemi avversari");
        moves.add("Mostra le tool card");
        moves.add("Mostra la RoundTrack");
        moves.add("Mostra il tuo schema");
        if(actions == null)
            return;
        for(String action: actions)
            moves.add(action);
        showMoves();
    }

    // show method



    public void showDiceSpace()
    {
        for(Dices d:diceSpace)
            System.out.print(d.toString());

        System.out.println("\n");
    }

    public void showOpponentsSchemas(HashMap<String,Schema> schema)
    {
        schema.remove(username);
        for(String key:schema.keySet()) {
            schema.get(key).splitImageSchema();
            schema.get(key).paint[0] = key;
        }
            showSchemas(schema);
         }

    public void showSchemas(HashMap<String,Schema> schema)
    {
        for(int i=0;i<PAINT_ROW;i++)
        {
            for(String key:schema.keySet()) {
                System.out.print(schema.get(key).paint[i]);
                if(i<3 || i>6) {
                    for (int j = schema.get(key).paint[i].length(); j < 45; j++)
                        System.out.print(" ");
                }
                else {
                    for (int x = 32; x < 46; x++)
                        System.out.print(" ");
                }
            }
            System.out.println("");
        }
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

        System.out.println(Colour.colorString("Scegli una tra le seguenti opzioni:",Colour.ANSI_YELLOW));
        for(int i=0;i<moves.size();i++)
            System.out.println((i+1)+")"+moves.get(i));
    }

    public void showRoundTrack()
    {
        if(roundTrack.size()==0)
            System.out.println(Colour.colorString("Round track vuota",Colour.ANSI_RED));

        for(int i=0;i<roundTrack.size();i++)
        {
            System.out.println("Round"+(i+1));
            for(Dices dices:roundTrack.get(i))
            System.out.print(dices.toString());
            System.out.println("\n");
    }
    }

    // action method

    public void startScene() {
        int choose;

        System.out.println(Colour.colorString("Prima di cominciare...",Colour.ANSI_YELLOW));
        while(!correct) {
            try {
                correct = true;
                System.out.println("Desideri:");
                System.out.println("1)Costruire il tuo schema");
                System.out.println("2)Caricarlo da file");
                System.out.println("3)Utilizzare schemi predefiniti");
                choose = Integer.parseInt(input.nextLine());
                switch(choose)
                {
                    case 1: createSchema(); break;
                    case 2: loadSchema(); break;
                    case 3: break;
                    default: correct = false;
                }
            }catch(NumberFormatException e)
            {
                System.out.println("Scelta non valida");
            }
        }

    }

    // used to load a custom schema
    public void loadSchema()
    {
        String name;
        boolean correct=false;
        final String path = "src/main/data/SchemaPlayer";
        File f = new File(path);
        Schema sc = new Schema();
        while(!correct) {
            System.out.println("Scegli il nome dello schema da caricare");
            if(f.list().length==0)
            {
                System.out.println(Colour.ANSI_RED.escape()+" Nessuno schema da caricare "+Colour.RESET);
                System.out.println("Crearne uno? y per si");
                if(input.nextLine().equals("y"))
                    createSchema();
                break;
            }
            for(String file:f.list())
            System.out.println(file.substring(0,file.length()-5));
            name = input.nextLine();
            try {
                schemas.put(username,sc.InitSchema("SchemaPlayer/"+name));
                correct = true;
            } catch (IOException e) {
                System.out.println("Errore con lo schema da caricare");
                correct = false;
            }
        }
    }


    // invoked by server to log the player
    public void login(String str) {
        if (str.equals("Welcome"))
        {
            System.out.println("La partita inizierà a breve");
            System.out.println("Aspettando altri giocatori...");
        }else if(str.equals("Login_error-username")) {
            System.out.println(Colour.colorString("Errore, nickname già in uso",Colour.ANSI_RED));
            this.setLogin();
        }else if(str.equals("Login_error-game")) {
            System.out.println(Colour.colorString("Errore, partita già in corso",Colour.ANSI_RED));
            System.out.println("Riprovare più tardi");
        }
    }

    // notifies the user that another player has joined the game
    public void playerConnected(String name){
        System.out.print("\r");
        System.out.println(name + " si è aggiunto alla lobby\n");
        nPlayer++;
    }

    // notifies the user that another player has left the game
    public void playerDisconnected(String name){
        System.out.print("\r");
        System.out.println(name + " si è disconnesso\n");
        nPlayer--;
    }

    // display timer connection
    public void timerPing(String time) {
        Colour colour = null;
            int percent =(int)(((LOBBY_TIMER_VALUE -Double.parseDouble(time))/ LOBBY_TIMER_VALUE)*100);
            System.out.print("\r"+Colour.colorString("L",Colour.ANSI_GREEN)+Colour.colorString("o",Colour.ANSI_RED)+Colour.colorString("a",Colour.ANSI_BLUE)+Colour.colorString("d",Colour.ANSI_YELLOW)+Colour.colorString("i",Colour.ANSI_PURPLE)+Colour.colorString("n",Colour.ANSI_GREEN)+Colour.colorString("g",Colour.ANSI_BLUE));
            for(int i=0;i<percent;i++)
            {
                switch(i/20)
                {
                    case 0:  colour = Colour.ANSI_GREEN;break;
                    case 1: colour = Colour.ANSI_RED;break;
                    case 2:colour = Colour.ANSI_BLUE;break;
                    case 3: colour = Colour.ANSI_YELLOW;break;
                    default:colour = Colour.ANSI_PURPLE;
                }

                System.out.print(Colour.colorString("▋",colour));
            }
            for(int i=percent;i<100;i++)
            {
                System.out.print(" ");
            }
        System.out.print(Colour.colorString(percent+"%",Colour.ANSI_BLUE));
    }

    // invoked when the game starts
    public void createGame(){
        clearScreen();
        System.out.println("\nPartita creata\n");
    }



    // invoked when user's schema has been accepted
    public void chooseSchema(String name)
    {



        try {
            Schema s = new Schema();
            schemas.put(this.getName(),s.InitSchema("SchemaClient/"+name));
            System.out.println("schema approvato: " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Aspettando la scelta degli altri giocatori...\n");
        if(schemaThread.isAlive())
        {
            System.out.println(Colour.colorString("Tempo scaduto! Schema scelto dal server!",Colour.ANSI_YELLOW));
            System.out.println(Colour.colorString("Premi invio!",Colour.ANSI_YELLOW));
            schemaThread.interrupt();
            try {
                schemaThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // invoked when the turn starts
    public void startTurn(String name)
    {
        clearScreen();
        try {
            load.displayImage("Round"+round+".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nIl tuo schema:");
        showMyschema();
        showDiceSpace();
        if(!name.equals(username)) {
            myTurn = false;
            System.out.println(Colour.colorString("Turno iniziato, tocca a: ",Colour.ANSI_BLUE) + name);
            setActions(null);
            showMoves();
        }
        else
        {
            myTurn = true;
            System.out.println(Colour.colorString("Tocca a te!",Colour.ANSI_BLUE));
        }
    }


    public void chooseMoves()
    {
        String move="";
        int choose;
        boolean correct = false;
        //to be modified later
        while(!correct)
        {
            correct = true;
            try
            {
                move = input.nextLine();
                if(move.equals(""))
                    throw new WrongInputException();

                choose = Integer.parseInt(move);
                if(choose>moves.size() || choose<1)
                    throw new WrongInputException();

                if (moves.get(choose - 1).equals("InsertDice")) {
                    insertDice();
                } else if (moves.get(choose - 1).equals("UseToolCard")) {
                    useToolCard();
                } else if (moves.get(choose - 1).equals("MoveDice")) {
                    moveDice();
                } else if (moves.get(choose - 1).equals("RollDice")) {
                    rollDice();
                } else if (moves.get(choose - 1).equals("EndTurn")) {
                    passTurn();
                } else if (moves.get(choose - 1).equals("DraftDice")) {
                    draftDice();
                } else if (moves.get(choose - 1).equals("PlaceDice")) {
                    placeDice();
                }else if (moves.get(choose - 1).equals("ChangeValue")) {
                    changeValue();
                }else if (moves.get(choose - 1).equals("SwapDice")) {
                    swapDice();
                }
                switch (choose - 1) {
                    case 0:
                        showPrivateObjective();
                        break;
                    case 1:
                        showPublicObjective();
                        break;
                    case 2:
                        showOpponentsSchemas((HashMap<String, Schema>) schemas.clone());
                        break;
                    case 3:
                        showToolCard();
                        break;
                    case 4:
                        showRoundTrack();
                        break;
                    case 5:
                        showMyschema();
                        break;
                    default: correct = false;
                }



            }catch (WrongInputException e) {
                e.getMessage();
                correct= false;
            }catch (NumberFormatException e)
            {
                System.out.println("Inserisci un numero");
                correct = false;
            }
        }
    }

    public void showMyschema()
    {
        schemas.get(username).splitImageSchema();
        schemas.get(username).showImage();
    }
    public void rollDice()
    {
        System.out.println("Vuoi davvero rollare il dado?");
        input.nextLine();
        System.out.println("Era retorico.");
        System.out.println("Rolliamo sto dado");
        connection.rollDice();
    }
    public void changeValue()
    {
        System.out.println("Vuoi incrementare(I) o decrementare(D) il dado?" );

        boolean correct = false;
        while(!correct) {

            String choose = input.nextLine();
            if (choose.equals("I"))
            {
                opVal = 1;
                connection.changeValue("Increment");

            }
            else if (choose.equals("D"))
            {
                opVal = -1;
                connection.changeValue("Decrement");
            }
            else{
                correct = false;
                System.out.println(Colour.colorString("Parametro non corretto",Colour.ANSI_RED));
            }
            correct = true;
        }
    }

    public void placeDice()
    {
        boolean correct= false;
        while(!correct) {
            try {
                System.out.println("Inserisci la riga in cui inserire il dado");
                row = Integer.parseInt(input.nextLine())-1;
                System.out.println("Inserisci la colonna in cui inserire il dado");
                column = Integer.parseInt(input.nextLine())-1;
                if(row<0 || row>3 || column<0 || column>4)
                    throw new NumberFormatException();
                correct = true;
                connection.sendPlaceDice(row,column);
            } catch (NumberFormatException ex) {
                System.out.println(Colour.colorString("Inserimento non valido",Colour.ANSI_RED));
                correct = false;
            }
        }
    }

    public void draftDice()
    {
        System.out.println("Inserisci l'indice del dado della riserva:(Da 1 a "+diceSpace.size()+")");
        indexDiceSpace = Integer.parseInt(input.nextLine())-1;
        connection.sendDraft(indexDiceSpace);
    }

    public void passTurn()
    {
        this.myTurn = false;
        connection.sendEndTurn();
    }

    public void insertDice()
    {
        correct = false;
        while(!correct) {
            try {
                System.out.println("Inserisci l'indice del dado della riserva:(Da 1 a "+diceSpace.size()+")");
                indexDiceSpace = Integer.parseInt(input.nextLine());
                indexDiceSpace--;
                if(indexDiceSpace<0 || indexDiceSpace>diceSpace.size())
                    throw new NumberFormatException();

                System.out.println("Inserisci la riga");
                row = Integer.parseInt(input.nextLine());
                row--;
                if(row<0 || row>3)
                    throw  new NumberFormatException();

                System.out.println("Inserisci la colonna");
                column = Integer.parseInt(input.nextLine());
                column --;
                if (column<0 || column>4)
                    throw  new NumberFormatException();

                correct = true;
                connection.insertDice(indexDiceSpace,row,column);

            }catch(NumberFormatException e) {
                System.out.println(Colour.colorString("Formato non valido",Colour.ANSI_RED));
            }
        }

    }

    public void clearScreen()
    {
        for(int i= 0 ;i<10;i++)
        System.out.println("\n");
    }

    public void useToolCard()
    {
        correct = false;
        System.out.println("Scegli la tool card da utilizzare:");
        for(String s:toolCard)
            System.out.println(s);
        int tool = Integer.parseInt(input.nextLine());
        //connection.sendMessage(tool);
        connection.useToolCard(tool);
    }

    public void useToolCardAccepted() {
        System.out.println("Carta utilizzata!");
    }

    public void useToolCardError() {
        System.out.println("Non hai abbastanza favori oppure non è permesso l'azione svolta dalla toolCard");
    }

    public void changeValueAccepted() {
        System.out.println(opVal);
        pendingDice.setNumber(pendingDice.getNumber()+opVal);
        System.out.println("Cambiamento valore accettato "+pendingDice);
    }

    public void changeValueError() {
        System.out.println("Errore di cambiamento del valore");
    }

    public void placeDiceAccepted() {
        System.out.println("Dado inserito correttamente");
        schemas.get(username).getGrid()[row][column].setNumber(pendingDice.getNumber());
        schemas.get(username).getGrid()[row][column].setColour(pendingDice.getColour());
        pendingDice = null;
    }

    public void rollDiceAccepted(int value) {
        pendingDice.setNumber(value);
        System.out.println("Valore del dado "+pendingDice);
    }

    List<List<Dices>> roundTrack = new ArrayList<List<Dices>>();
    public void pickDiceRoundTrack(List action) {
        pendingDice = roundTrack.get(Integer.parseInt((String)action.get(0))).get(Integer.parseInt((String)action.get(1)));
        roundTrack.get(Integer.parseInt((String)action.get(0))).remove(Integer.parseInt((String)action.get(1)));
    }

    public void pickDiceRoundTrackError() {
        System.out.println(Colour.colorString("Errore,dado non trovato",Colour.ANSI_RED));
    }

    public void placeDiceRoundTrack(List action) {
        int roundNumber = Integer.parseInt((String)action.get(0));
        if(roundNumber > roundTrack.size() -1)
            roundTrack.add(new ArrayList<Dices>());
        action.remove(0);
        for(int i=0; i<action.size(); i+=2){
            roundTrack.get(roundNumber).add(new Dices("",Integer.parseInt((String)action.get(i+1)),Colour.stringToColour((String)action.get(i))));
        }
    }

    public void swapDiceAccepted() {
        System.out.println("Dado scambiato correttamente");
    }

    public void swapDice(){
        correct = false;
        while(!correct) {
            try {
                System.out.println("Inserisci il numero del round da cui prendere il dado");
                int nRound = Integer.parseInt(input.nextLine()) - 1;
                System.out.println(roundTrack.get(nRound));
                System.out.println("Inserisci il numero del dado che vuoi prendere dalla RoundTrack");
                int index = Integer.parseInt(input.nextLine()) - 1;
                connection.swapDice(nRound, index);
                correct = true;
            }catch(NumberFormatException e)
            {
                System.out.println(Colour.colorString("Parametro non valido",Colour.ANSI_RED));
                correct = false;
            }
        }
    }

    public void moveDice() {
        correct = false;
        while (!correct) {
            try {
                System.out.println("Inserisci l'indice della riga da cui prendere il dado:");
                oldRow = Integer.parseInt(input.nextLine()) - 1;
                System.out.println("Inserisci l'indice della colonna da cui prendere il dado:");
                oldColumn = Integer.parseInt(input.nextLine()) - 1;
                System.out.println("Inserisci l'indice della riga in cui spostare il dado:");
                newRow = Integer.parseInt(input.nextLine()) - 1;
                System.out.println("Inserisci l'indice della colonna in cui spostare il dado:");
                newColumn = Integer.parseInt(input.nextLine()) - 1;
                connection.moveDice(oldRow, oldColumn, newRow, newColumn);
                correct = true;
            }catch(NumberFormatException e) {
                System.out.println(Colour.colorString("Parametro non valido", Colour.ANSI_RED));
                correct = false;
            }
        }
    }

    public void draftDiceAccepted()
    {
        System.out.println("Azione accettata");
        pendingDice = diceSpace.get(indexDiceSpace);
        System.out.println(pendingDice);
    }
    public void moveDiceAccepted(){
        this.schemas.get(username).getGrid()[newRow][newColumn].setNumber(this.schemas.get(username).getGrid()[oldRow][oldColumn].getNumber());
        this.schemas.get(username).getGrid()[newRow][newColumn].setColour(this.schemas.get(username).getGrid()[oldRow][oldColumn].getColour());
        this.schemas.get(username).getGrid()[oldRow][oldColumn].setNumber(0);
        this.schemas.get(username).getGrid()[oldRow][oldColumn].setColour(null);

        schemas.get(username).splitImageSchema();
        schemas.get(username).showImage();
        System.out.println("Dado spostato correttamente");
    }

    public void pickDiceSchema(List action){
        if(!action.get(0).equals(username)){
            this.schemas.get(action.get(0)).getGrid()[Integer.parseInt((String)action.get(1))][Integer.parseInt((String)action.get(2))] =
                    new Dices("",0,null);
        }
    }

    public void pickDiceSchemaError(){
        System.out.println("Dado non trovato");
    }

    public String getName(){ return this.username;}

}

