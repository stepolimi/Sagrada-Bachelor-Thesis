package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.costants.GameCreationMessages.endTurn;



public class ViewCLI implements View{
    private Scanner input;
    private String username="";
    private Connection connection;
    private Handler hand;
    private ArrayList<Schema> schemas ; // size = numPlayer
    private String schemaChoose;
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
    public ViewCLI()
    {
        schemaChoose = "";
        input = new Scanner(System.in);
        schemas = new ArrayList<Schema>();
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
        String nameSchema;
        nameSchema = input.nextLine();
        connection.sendMessage(nameSchema);
        schemaChoose = nameSchema;
        System.out.println("\n");
    }

    public void setDiceSpace(ArrayList dice)
    {
        for(int i=0;i<dice.size();i=i+2)
        {
            diceSpace.add(new Dices("",Integer.parseInt((String) dice.get(i)),(Colour)dice.get(i+1)));
        }
    }


    public void setDice(int player)
    {
        this.schemas.get(player).getGrid()[row][column]= diceSpace.get(indexDiceSpace);
        diceSpace.remove(indexDiceSpace);
        moves.remove("Inserisci un dado");
    }

    public void setMoves()
    {
        moves.add("Mostra l'obiettivo privato");
        moves.add("Mostra gli obiettivi pubblici");
        moves.add("Mostra schemi avversari");
        moves.add("Mostra le tool card");
        moves.add("Passa il turno");
        moves.add("Inserisci un dado");
        moves.add("Usa una toolcard");

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
        for(String sch:s) {
            try {
                schemas.add(temp.InitSchema(sch));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            System.out.println(d.toString());
        }
    }

    public void showOpponentsSchemas()
    {
        for(int i=1;i<schemas.size();i++)
            System.out.println(schemas.get(i).toString());
    }
    public void showPrivateObjective()
    {
        System.out.println("Il tuo obiettivo privato è:"+publicObjcective);
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
        System.out.println(name + " si è aggiunto alla lobby\n");
    }

    public void playerDisconnected(String name){
        System.out.println(name + " si è disconnesso\n");
    }

    public void timerPing(String time) {
        System.out.println("la partita inizierà tra " + time + " secondi\n");
    }

    public void createGame(){
        System.out.println("partita creata\n");
    }




    public void schemaChoose()
    {
        try {
            schemas.get(0).InitSchema(schemaChoose);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTurn()
    {
        while(myTurn) {
            System.out.println("Il tuo schema:");
            System.out.println(schemas.get(0).toString());
            showDiceSpace();
            setMoves();
            showMoves();
            chooseMoves();
        }
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
            case 4: passTurn(); break;
        }

    }



    public void passTurn()
    {
        this.myTurn = false;
        connection.sendMessage(endTurn);
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

