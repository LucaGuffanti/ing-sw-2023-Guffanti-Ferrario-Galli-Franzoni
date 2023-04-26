package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;

import java.io.Serializable;

public abstract class ClientConnection implements Serializable {
    private boolean isConnected;
    private String name;
    public abstract void sendMessage(Message m);

    public ClientConnection(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getName() {
        return name;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void setName(String name) {
        this.name = name;
    }
}
