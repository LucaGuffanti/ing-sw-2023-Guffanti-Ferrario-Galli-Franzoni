package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.ConnectionEstablishedMessage;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class containing a thread that sends messages to the web through sockets, used to permit parallel
 * sending and receiving of messages by the client. The thread is activated one the client receives a
 *  * {@link ConnectionEstablishedMessage} and is interrupted once an error occurs
 * @author Luca Guffanti
 */
public class SocketSender {
    Socket socket;
    ObjectOutputStream out;
    ClientNetworkHandler clientNetworkHandler;

    public SocketSender(Socket socket, ObjectOutputStream out, ClientNetworkHandler clientNetworkHandler) {
        this.socket = socket;
        this.out = out;
        this.clientNetworkHandler = clientNetworkHandler;
    }

    public void sendMessage(Message m) {
        try {
            out.writeObject(m);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't reach the server");
        }
    }

}
