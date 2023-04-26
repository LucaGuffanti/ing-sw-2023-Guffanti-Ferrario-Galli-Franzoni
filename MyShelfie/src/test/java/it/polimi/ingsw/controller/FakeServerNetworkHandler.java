package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.server.controller.GameController;
import jdk.jfr.Label;

import java.util.HashMap;

public class FakeServerNetworkHandler extends ServerNetworkHandler {
    private GameController controller = null;

    @Label("DEBUG")
    private Message currentMessage;
    public void sendToPlayer(String recipient, Message message) {
        currentMessage = message;
        Logger.networkInfo("sent a " + message.getType() + " private message to "+ recipient);
    }

    public void broadcastToAllButSender(String sender, Message message) {
        currentMessage = message;
        Logger.networkInfo("broadcast a " + message.getType() + " message to all but " + sender);
    }

    public void broadcastToAll(Message message) {
        currentMessage = message;
        Logger.networkInfo("broadcast a " + message.getType() + " message");
    }

    @Label("DEBUG")
    public Message getCurrentMessage() {
        return currentMessage;
    }
}
