package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.ChatHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage extends Message {

    private String value;
    private String senderName;

    private LocalDateTime sendingTime;

    public ChatMessage(String value, String senderName, LocalDateTime sendingTime) {
        super(MessageType.CHAT_MESSAGE, senderName);
        this.value = value;
        this.senderName = senderName;
        this.sendingTime = sendingTime;
    }

    public String getFormattedMessage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = sendingTime.format(formatter);
        return dateTime+" "+senderName+": "+value;
    }


    @Override
    public MessagesHandler getHandlerForClient() {
        return new ChatHandler();
    }
}
