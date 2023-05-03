package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.ChatMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatMessage extends Message {

    private String body;
    private String senderName;

    private LocalDateTime sendingTime;
    private List<String> recipients;

    public ChatMessage(String body, String senderName, LocalDateTime sendingTime, List<String> recipients) {
        super(MessageType.CHAT_MESSAGE, senderName);
        this.body = body;
        this.senderName = senderName;
        this.sendingTime = sendingTime;
        this.recipients = recipients;
    }

    public String getFormattedMessage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = sendingTime.format(formatter);
        return dateTime+" "+senderName+": "+body;
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = sendingTime.format(formatter);
        return dateTime;
    }

    public String getBody() {
        return body;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new ChatMessageHandler();
    }

    public List<String> getRecipients() {
        return recipients;
    }
}
