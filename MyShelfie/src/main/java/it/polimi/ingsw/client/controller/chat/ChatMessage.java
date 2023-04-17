package it.polimi.ingsw.client.controller.chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {

    private String value;
    private String senderName;

    private LocalDateTime sendingTime;

    public ChatMessage(String value, String senderName, LocalDateTime sendingTime) {
        this.value = value;
        this.senderName = senderName;
        this.sendingTime = sendingTime;
    }

    public String getFormattedMessage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = sendingTime.format(formatter);
        return dateTime+" "+senderName+": "+value;
    }


}
