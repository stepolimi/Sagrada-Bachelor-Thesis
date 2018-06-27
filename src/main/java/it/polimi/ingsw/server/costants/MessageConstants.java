package it.polimi.ingsw.server.costants;

//messages in-game between server and client


public class MessageConstants {
    private MessageConstants() {}

    //server -> client messages
    public static final String LOGIN_ERROR = "Login_error";
    public static final String LOGIN_SUCCESSFUL = "Welcome";
    public static final String TIMER_PING = "timerPing";
    public static final String LOGOUT = "logout";
    public static final String WELCOME_BACK = "welcomeBack";
    public static final String START_GAME = "startingGameMsg";
    public static final String SET_PRIVATE_CARD = "setPrivateCard";
    public static final String SET_SCHEMAS = "setSchemas";
    public static final String SET_PUBLIC_OBJECTIVES = "setPublicObjectives";
    public static final String SET_TOOL_CARDS = "setToolCards";
    public static final String APPROVED_SCHEMA = "okSchema";
    public static final String APPROVED_SCHEMA_CUSTOM = "okSchemaCustom";
    public static final String SET_OPPONENTS_SCHEMAS = "setOpponentsSchemas";
    public static final String SET_OPPONENTS_CUSTOM_SCHEMAS = "setOpponentsCustomSchemas";
    public static final String START_TURN = "startTurn";
    public static final String START_ROUND = "startRound";
    public static final String SET_ACTIONS = "setActions";
    public static final String SET_DICE_SPACE = "setDiceSpace";
    public static final String PICK_DICE_SCHEMA = "pickDiceSchema";
    public static final String PICK_DICE_SCHEMA_ERROR = "pickDiceSchemaError";
    public static final String PLACE_DICE_SCHEMA = "placeDiceSchema";
    public static final String PLACE_DICE_SCHEMA_ERROR = "placeDiceSchemaError";
    public static final String PLACE_DICE_DICESPACE = "placeDiceSpace";
    public static final String PICK_DICE_SPACE = "pickDiceSpace";
    public static final String PICK_DICE_SPACE_ERROR = "pickDiceSpaceError";
    public static final String PLACE_DICE_ROUND_TRACK = "placeDiceRoundTrack";
    public static final String PICK_DICE_ROUND_TRACK = "pickDiceRoundTrack";
    public static final String PICK_DICE_ROUND_TRACK_ERROR = "pickDiceRoundTrackError";
    public static final String INSERT_DICE_ACCEPTED = "insertDiceAccepted";
    public static final String USE_TOOL_CARD_ACCEPTED = "useToolCardAccepted";
    public static final String USE_TOOL_CARD_ERROR = "useToolCardError";
    public static final String MOVE_DICE_ACCEPTED = "moveDiceAccepted";
    public static final String MOVE_DICE_ERROR = "moveDiceError";
    public static final String DRAFT_DICE_ACCEPTED = "draftDiceAccepted";
    public static final String PLACE_DICE_ACCEPTED = "placeDiceAccepted";
    public static final String SWAP_DICE_ACCEPTED = "swapDiceAccepted";
    public static final String PLACE_DICE_SPACE_ACCEPTED = "placeDiceSpaceAccepted";
    public static final String CANCEL_USE_TOOL_CARD_ACCEPTED = "cancelUseToolCardAccepted";
    public static final String CHANGE_VALUE_ACCEPTED = "ChangeValueAccepted";
    public static final String CHANGE_VALUE_ERROR = "ChangeValueError";
    public static final String CHOOSE_VALUE_ACCEPTED = "chooseValueAccepted";
    public static final String CHOOSE_VALUE_ERROR = "chooseValueError";
    public static final String FLIP_DICE_ACCEPTED = "flipDiceAccepted";
    public static final String ROLL_DICE_SPACE_ACCEPTED = "rollDiceSpaceAccepted";
    public static final String ROLL_DICE_ACCEPTED = "RollDiceAccepted";
    public static final String SWAP_DICE_BAG_ACCEPTED = "swapDiceBagAccepted";
    public static final String SET_WINNER = "winner";
    public static final String SET_RANKINGS = "setRankings";
    public static final String SET_SCHEMAS_ON_RECONNECT = "setSchemasOnReconnect";

    //client -> server messages
    public static final String DISCONNECTED = "Disconnected";
    public static final String LOGIN = "Login";
    public static final String MOVE_DICE = "MoveDice";
    public static final String PLACE_DICE = "PlaceDice";
    public static final String PLACE_DICE_SPACE = "PlaceDiceSpace";
    public static final String CHANGE_VALUE = "ChangeValue";
    public static final String ROLL_DICE = "RollDice";
    public static final String SWAP_DICE = "SwapDice";
    public static final String CANCEL_USE_TOOL_CARD = "CancelUseToolCard";
    public static final String FLIP_DICE = "FlipDice";
    public static final String ROLL_DICE_SPACE = "RollDiceSpace";
    public static final String SWAP_DICE_BAG = "SwapDiceBag";
    public static final String CHOOSE_VALUE = "ChooseValue";
    public static final String CUSTOM_SCHEMA = "CustomSchema";
    public static final String CHOOSE_SCHEMA = "ChooseSchema";
    public static final String DRAFT_DICE = "DraftDice";
    public static final String USE_TOOL_CARD = "UseToolCard";
    public static final String END_TURN ="EndTurn";
    public static final String INSERT_DICE = "InsertDice";


    // in server messages
    public static final String SET_PUBLIC_OBJECTIVES_ON_RECONNECT = "setPublicObjectivesOnRec";
    public static final String SET_TOOL_CARDS_ON_RECONNECT = "setToolCardsOnRec";
    public static final String SET_DICE_SPACE_ON_RECONNECT = "setDiceSpaceOnRec";
    public static final String PLACE_DICE_ROUND_TRACK_ON_RECONNECT = "placeDiceRoundTrackOnRec";
}
