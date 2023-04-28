package it.polimi.ingsw.network.utils;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This class implements methods that deserialize a json object representing a message,
 * converting it back to the message object (with a correct dynamic type).
 * @author Luca Guffanti
 */
public class JSONMessageDeserializer {

    /**
     *
     * @param messageJSON the JSON object describing the message
     * @return a new <b> Message </b>with dynamic type being equal to the one defined in the attribute of each message.
     * @see MessageType
     */
    public static Message deserializeMessage(String messageJSON) {
        Gson gson = new Gson();
        Message m = gson.fromJson(messageJSON, Message.class);

        switch (m.getType()) {
            case CONNECTION_ESTABLISHED -> {return new Deserializer<ConnectionEstablishedMessage>().deserialize(messageJSON, ConnectionEstablishedMessage.class);}
            case LOGIN_REQUEST -> {return new Deserializer<LoginRequestMessage>().deserialize(messageJSON, LoginRequestMessage.class);}
            case LOGIN_RESPONSE -> {return new Deserializer<LoginResponseMessage>().deserialize(messageJSON, LoginResponseMessage.class);}
            case JOIN_GAME -> {return new Deserializer<JoinGameMessage>().deserialize(messageJSON, JoinGameMessage.class);}
            case PICK_NUMBER_OF_PLAYERS -> {return new Deserializer<PickNumberOfPlayersMessage>().deserialize(messageJSON, PickNumberOfPlayersMessage.class);}
            case NUMBER_OF_PLAYERS_SELECTION -> {return new Deserializer<NumberOfPlayersSelectionMessage>().deserialize(messageJSON, NumberOfPlayersSelectionMessage.class);}
            case ACCESS_RESULT -> {return new Deserializer<AccessResultMessage>().deserialize(messageJSON, AccessResultMessage.class);}
            case NEW_PLAYER -> {return new Deserializer<NewPlayerMessage>().deserialize(messageJSON, NewPlayerMessage.class);}
            case GAME_START -> {return new Deserializer<GameStartMessage>().deserialize(messageJSON, GameStartMessage.class);}
            case BEGINNING_OF_TURN -> {return new Deserializer<BeginningOfTurnMessage>().deserialize(messageJSON, BeginningOfTurnMessage.class);}
            case PICK_FROM_BOARD -> {return new Deserializer<PickFromBoardMessage>().deserialize(messageJSON, PickFromBoardMessage.class);}
            case PICK_FROM_BOARD_RESULT -> {return new Deserializer<PickFromBoardResultMessage>().deserialize(messageJSON, PickFromBoardResultMessage.class);}
            case SELECT_COLUMN -> {return new Deserializer<SelectColumnMessage>().deserialize(messageJSON, SelectColumnMessage.class);}
            case SELECT_COLUMN_RESULT -> {return new Deserializer<SelectColumnResultMessage>().deserialize(messageJSON, SelectColumnResultMessage.class);}
            case END_OF_TURN -> {return new Deserializer<EndOfTurnMessage>().deserialize(messageJSON, EndOfTurnMessage.class);}
            case END_OF_GAME -> {return new Deserializer<EndOfGameMessage>().deserialize(messageJSON, EndOfGameMessage.class);}

            case PING_REQUEST -> {return new Deserializer<PingRequestMessage>().deserialize(messageJSON, PingRequestMessage.class);}
            case REJOIN_GAME -> {return new Deserializer<ReJoinGameMessage>().deserialize(messageJSON, ReJoinGameMessage.class);}

            default -> { throw new IllegalStateException("Unexpected value: " + m.getType()); }
        }
    }

    static class Deserializer<T extends Message> {
        public <T extends Message> T deserialize(String messageJSON, Class<T> type) {
            return new Gson().fromJson(messageJSON, type);
        }
    }
}
