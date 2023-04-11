package it.polimi.ingsw.network.messages;

/**
 * A message is the object that is sent between clients and the server through the network, and it contains information
 * about moves done by the players, about communication logistics (login of a client, disconnection, ping), and about the state of
 * the game. <br>
 *
 * Every message has a header containing the type of the message{@link MessageType}, used to recognize the message
 * and correctly exploit polymorphism, and a payload that varies based on the type of the message
 *
 */
public abstract class Message {
    /**
     * The type of the message
     */
    MessageType type;

    public Message(MessageType type){
        this.type = type;
    }

    public abstract void printMessage();
}
