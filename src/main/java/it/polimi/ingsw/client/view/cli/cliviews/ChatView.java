package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.ChatMessage;

import java.util.List;

/**
 * The chat view is accessed when the user wants to display chat messages.
 * It's important to notice that it's still possible to send chat messages when not in this particular
 * view.
 * @author Luca Guffanti
 */
public class ChatView implements CliView {
    /**
     * Display the entire chat
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.title("CHAT");
        List<ChatMessage> messages = state.getChatHistory();
        for (int i = 0; i < messages.size(); i++) {
           Printer.printChatMessage(messages.get(i), state.getUsername());
        }
    }

    public void updateRender(ClientState state) {
        Printer.printChatMessage(state.getLastChatMessage(), state.getUsername());
    }
}