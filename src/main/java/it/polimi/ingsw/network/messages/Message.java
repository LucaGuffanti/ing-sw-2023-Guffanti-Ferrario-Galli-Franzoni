package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

import java.io.Serializable;

/**
 * A message is the object that is sent between clients and the server through the network, and it contains information
 * about moves done by the players, about communication logistics (login of a client, disconnection, ping), and about the state of
 * the game. <br>
 *
 * Every message has a header containing the type of the message{@link MessageType}, used to recognize the message
 * and correctly exploit polymorphism, and a payload that varies based on the type of the message
 * <br>
 * Every message also has two constructors: one with a text description that may be printed
 * and the other without.
 */
public abstract class Message implements Serializable {
    /**
     * The type of the message
     */
    protected final MessageType type;
    protected final String senderUsername;
    protected String description;


    public Message(MessageType type, String senderUsername){
        this.type = type;
        this.senderUsername = senderUsername;
    }

    public Message(MessageType type, String senderUsername, String description){
        this.type = type;
        this.senderUsername = senderUsername;
        this.description = description;
    }

    public void printMessage() {
        System.out.println(type.toString());
        if (description != null) {
            System.out.println(description);
        }
    }
    public MessageType getType() {
        return type;
    }
    public String getDescription() {
        return description;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Get the right handler for the client to use the Message payload when received.
     */
    public abstract MessagesHandler getHandlerForClient();
}
