package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;

public interface ClientNetworkInterface {
    void sendMessage(Message toSend);
    void login(String name);
    void init();
}
