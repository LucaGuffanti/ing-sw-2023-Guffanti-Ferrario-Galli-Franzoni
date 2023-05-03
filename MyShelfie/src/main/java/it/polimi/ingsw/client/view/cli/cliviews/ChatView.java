package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.server.model.Board;

import java.util.List;

public class ChatView implements CliView {
    @Override
    public void render(ClientState state) {
        Printer.title("CHAT");
        List<ChatMessage> messages = state.getChatHistory();
        for (int i = 0; i < messages.size(); i++) {
           Printer.printChatMessage(messages.get(i));
        }
    }

    public void updateRender(ClientState state) {
        Printer.printChatMessage(state.getLastChatMessage());
    }
}