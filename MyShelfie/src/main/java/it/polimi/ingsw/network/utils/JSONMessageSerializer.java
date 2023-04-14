package it.polimi.ingsw.network.utils;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.Message;

/**
 * This class implements a method that serializes messages, converting to object to a json representation,
 * that's used in the RMI communication.
 * @author Luca Guffanti
 */
public class JSONMessageSerializer {
    public static String serializeMessage(Message m) {
        Gson gson = new Gson();
        String serialized = gson.toJson(m);
        return serialized;
    }
}
