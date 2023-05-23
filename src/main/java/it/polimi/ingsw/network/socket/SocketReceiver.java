package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.ConnectionEstablishedMessage;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

/**
 * Class containing a thread that receives messages to from the server through sockets, used to permit parallel
 * sending and receiving of messages by the client. The thread is activated one the client receives a
 * {@link ConnectionEstablishedMessage} and is interrupted once an error occurs
 *
 * @author Luca Guffanti
 */
public class SocketReceiver extends Thread {
    Socket socket;
    ObjectInputStream in;
    ClientNetworkHandler clientNetworkHandler;

    public SocketReceiver(Socket socket, ObjectInputStream in, ClientNetworkHandler clientNetworkHandler) {
        this.socket = socket;
        this.in = in;
        this.clientNetworkHandler = clientNetworkHandler;
    }

    @Override
    public void run() {
        List<Message> queue = null;
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Message received = (Message) in.readObject();
                // System.out.println(received.getType());
                clientNetworkHandler.handleIncomingMessage(received);
                /*synchronized (clientNetworkHandler.getMessageQueue()) {
                    clientNetworkHandler.getMessageQueue().add(received);
                    clientNetworkHandler.getMessageQueue().notifyAll();
                }*/
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Lost connection with the server!");
                clientNetworkHandler.onConnectionLost();
                Thread.currentThread().interrupt();
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                System.out.println("Couldn't cast to Message");
                clientNetworkHandler.onConnectionLost();
                Thread.currentThread().interrupt();
            }
        }
    }
}
