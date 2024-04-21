package se.iths.springbootgroupproject.services;

import jakarta.persistence.EntityNotFoundException;


import org.springframework.stereotype.Service;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.dtos.PublicMessage;
import se.iths.springbootgroupproject.repos.MessageRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    MessageRepository messageRepository;
    TranslationService translationService;

    public MessageService(MessageRepository messageRepository, TranslationService translationService) {
        this.messageRepository = messageRepository;
        this.translationService = translationService;
    }

    //    @PreAuthorize("#updateMessage.user.getId() == authentication.principal.id")
    public Message updateMessage(Long id, Message updateMessage) {
        Message existingMessage = messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + id));
        if (updateMessage.getCreatedDate() != null) {
            existingMessage.setCreatedDate(updateMessage.getCreatedDate());
        }

        if (updateMessage.getMessageTitle() != null) {
            existingMessage.setMessageTitle(updateMessage.getMessageTitle());
        }

        if (updateMessage.getMessageBody() != null) {
            existingMessage.setMessageBody(updateMessage.getMessageBody());
        }

        if (updateMessage.getUser() != null) {
            existingMessage.setUser(updateMessage.getUser());
        }

        existingMessage.setLastModifiedDate(LocalDate.now());


        existingMessage.setPublic(updateMessage.isPublic());

        return saveMessage(existingMessage);
    }


    public Message saveMessage(Message message) {
        String language = translationService.detectMessageLanguage(message.getMessageBody());
        message.setMessageLanguage(language);
        messageRepository.save(message);
        return message;
    }


    public List<PublicMessage> findAllByPrivateMessageIsFalse() {
        return messageRepository.findAllByIsPublicIsTrue();
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public List<Message> fidAllByUserId(Long userId) {
        return messageRepository.findAllByUserId(userId);
    }


    public List<Message> getPage(int p, int i) {
//        return messageRepository.findAll(PageRequest.of(p,i)).toList();
        return messageRepository.findMessagesBy(p,i);
    }

    public List<Message> getPaginatedUserMessages(int p, int i, Long user) {
//
        return messageRepository.findMessagesByUserId(p, i, user);
    }

    public int getUserMessagesCount(Long userId) {
        return messageRepository.countByUserId(userId);
    }

    public long messageCount() {
        return messageRepository.count();
    }


//    public Message translateMessage(Message message) {
//        String translatedTitle = translationService.translateText(message.getMessageTitle());
//        message.setMessageTitle(translatedTitle);
//        String translatedBody = translationService.translateText(message.getMessageBody());
//        message.setMessageBody(translatedBody);
//        return message;
//    }

}
