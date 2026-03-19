package it.unisa.HAYT.servicies;

import it.unisa.HAYT.entities.MessageEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.MessageRepository;
import it.unisa.HAYT.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public MessageEntity saveMessage(Long senderId, Long receiverId, String content) {
        UserEntity sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        UserEntity receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        MessageEntity message = new MessageEntity();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<MessageEntity> getChatMessages(Long userId1, Long userId2) {
        return messageRepository.findChatMessagesBetweenUsers(userId1, userId2);
    }

}
