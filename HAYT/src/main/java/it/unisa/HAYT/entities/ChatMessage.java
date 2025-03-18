package it.unisa.HAYT.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private Long senderId;
    private Long receiverId;
    private String content;
}

