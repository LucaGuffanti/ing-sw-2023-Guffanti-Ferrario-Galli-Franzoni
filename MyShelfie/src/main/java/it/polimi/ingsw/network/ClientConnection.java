package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;

import java.io.Serializable;

public interface ClientConnection extends Serializable {
    void sendMessage(Message m);
}
