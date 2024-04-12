package se.iths.springbootgroupproject.controllers;

import org.springframework.stereotype.Controller;
import se.iths.springbootgroupproject.services.MessageService;

@Controller
public class HomeController {

    MessageService messageService;

    public HomeController(MessageService messageService) {
        this.messageService = messageService;
    }




}
