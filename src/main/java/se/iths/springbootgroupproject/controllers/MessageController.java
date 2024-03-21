package se.iths.springbootgroupproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repos.MessageRepository;
import se.iths.springbootgroupproject.services.MessageService;

import java.util.List;


@RestController
public class MessageController {

    MessageRepository messageRepository;
    MessageService messageService;

    public MessageController(MessageRepository messageRepository, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();

        System.out.println("All messages are read from the database!");

        return messages;

    }
}
