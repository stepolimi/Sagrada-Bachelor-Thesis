package it.polimi.ingsw.client.view.gui;

public class GameMessage {

    private GameMessage(){}


    //Connection Message
    static final String NEW_PLAYER = " si è aggiunto alla lobby......\n";
    static final String PLAYER_DISCONNECTED = " si è disconnesso........\n";
    static final String CONNECTION_ERROR = "Errore di connessione";



    //message about Turn and Round
    static final String NEW_ROUND = "Nuovo Round Iniziato";
    static final String MY_TURN = "Tocca a te!!!!!";
    static final String NOT_MY_TURN = "turno iniziato, tocca a: ";

    static final String FULL = "full";
    static final String EMPTY = "";


    //message AFTER use of Tool Card
    static final String USE_TOOL_5 = "Ora scegli il dado dal tracciato di Round";
    static final String USE_TOOL_6 = "Clicca sul dado preso e lancialo!";
    static final String USE_TOOL_7 = "Puoi utilizzare la Carta Utensile! Clicca nuovamente sulla carta per lanciare i dadi!";
    static final String USE_TOOL_11 = "Ora clicca sul dado per sostituire il dado con uno del sacchetto!";
    static final String USE_TOOL_GENERIC = "Puoi utilizzare la Carta Utensile! Procedi";
    static final String TOOL_USED = "Hai usato la Carta Utensile!";
    static final String TOOL_NOT_USE = "Non hai usato la carta utensile!";

    //message DURING use of Tool Card
    static final String DICE_SPACE_ROLLED = "Hai utilizzato la Carta Utensile! Ora puoi inserire un dado";
    static final String PLACE_DICE_CHOOSEN = "Hai cambiato correttamente il dado! Ora inseriscilo!";
    static final String PLACE_DICE_CHANGED = "Hai cambiato valore! Ora inseriscilo!";
    static final String PLACE_DICE_ROOLED = "Dado tirato! Ora piazzalo";
    static final String PLACE_DICE_SWAPPED = "Hai scambiato il dado! Ora Piazzalo!";
    static final String DICE_ACCEPTED = "Dado Accettato!";
    static final String FIRST_DICE_TOOL_4_12_OK = "Hai inserito il primo dado. Inserisci il secondo!";
    static final String DICE_DECREMENT = "decrement";

    //Error during use of Tool Card
    static final String CHANGE_VALUE_ERROR = "Non puoi incrementare un 6 o decrementare un 1!";
    static final String PLACE_DICE_ERROR = "Errore di piazzamento. Clicca sul dado e riposizionalo";
    static final String PICK_DICE_ERROR = "Errore nel prendere il dado!";
    static final String PICK_DICE_ROUND_ERROR = "Errore. Dado non trovato. Riprova!";
    static final String PICK_DICE_SCHEMA_ERROR = " Non ci sono dadi qui!";
    static final String CHOOSE_VALUE_ERROR = "Azione non corretta. Riprova!";
    static final String USE_TOOL_CARD_ERROR = "Non puoi usare la Carta Utensile ora!";

    //Error generic Dice Insertion
    static final String WRONG_INSERTION = "Inserimento non corretto. Riprovare";



    //Color constant passed by Server
    static final String BLUE = "ANSI_BLUE";
    static final String GREEN = "ANSI_GREEN";
    static final String RED = "ANSI_RED";
    static final String YELLOW = "ANSI_YELLOW";

    //Editor of Schema
    static final String CHOOSE_DESTINATION = "Scegli dove salvare il tuo schema";
    static final String NO_DIRECTORY = "No Directory selected";




}
