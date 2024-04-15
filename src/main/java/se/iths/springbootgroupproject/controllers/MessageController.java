package se.iths.springbootgroupproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.services.MessageService;
import se.iths.springbootgroupproject.services.TranslationService;
import se.iths.springbootgroupproject.services.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/messages")
public class MessageController {
    MessageService messageService;
    UserService userService;
    TranslationService translationService;

    public MessageController(MessageService messageService, UserService userService, TranslationService translationService) {
        this.messageService = messageService;
        this.userService = userService;
        this.translationService = translationService;
    }


    @GetMapping("messages")
    public String messages(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Principal principal, HttpServletRequest httpServletRequest, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (page < 0) page = 0;
        var paginatedMessages = messageService.getPage(0, size);
        long messagesCount = messageService.findAll();

        boolean isLoggedIn = principal != null;

        Integer githubId;
        Optional<User> loggedInUser = Optional.empty();

        if (oauth2User != null) {
            githubId = oauth2User.getAttribute("id");
            loggedInUser = userService.findByUserId(githubId);
        }

        model.addAttribute("page", page);
        model.addAttribute("messagesCount", messagesCount);
        model.addAttribute("nextpage", paginatedMessages.getLast().getId());
        model.addAttribute("messages", paginatedMessages);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("httpServletRequest", httpServletRequest);
        loggedInUser.ifPresent(user -> model.addAttribute("loggedInUser", user));

        return "messages/messages";
    }

    @GetMapping("allmessages")
    public String allMessages(Model model, @RequestParam(defaultValue = "5") int size, Principal principal,
                              @AuthenticationPrincipal OAuth2User oauth2User) {


        var paginatedMessages = messageService.getPage(0, size);
        long messagesCount = messageService.findAll();

        boolean isLoggedIn = principal != null;

        Integer githubId;
        Optional<User> loggedInUser = Optional.empty();

        if (oauth2User != null) {
            githubId = oauth2User.getAttribute("id");
            loggedInUser = userService.findByUserId(githubId);
        }

        model.addAttribute("messagesCount", messagesCount);
        model.addAttribute("nextpage", paginatedMessages.getLast().getId());
        model.addAttribute("messages", paginatedMessages);
        model.addAttribute("isLoggedIn", isLoggedIn);
        loggedInUser.ifPresent(user -> model.addAttribute("loggedInUser", user));

        return "messages/allmessages";
    }


    @GetMapping("nextpage")
    public String loadMore(Model model, @RequestParam(defaultValue = "1") String page, Principal principal,
                           @AuthenticationPrincipal OAuth2User oauth2User, @RequestParam(defaultValue = "5") int size) {
        int p = Integer.parseInt(page);
        var messages = messageService.getPage(p, size);
        boolean isLoggedIn = principal != null;
        long messagesCount = messageService.findAll();

        Integer githubId;
        Optional<User> loggedInUser = Optional.empty();

        if (oauth2User != null) {
            githubId = oauth2User.getAttribute("id");
            loggedInUser = userService.findByUserId(githubId);
        }

        model.addAttribute("page", p);
        model.addAttribute("messagesCount", messagesCount);
        model.addAttribute("messages", messages);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("nextpage", messages.getLast().getId());
        loggedInUser.ifPresent(user -> model.addAttribute("loggedInUser", user));
        return "messages/nextpage";
    }

    @GetMapping("usermessages")
    public String getUserMessages(@RequestParam Long userId, Model model, @RequestParam(defaultValue = "1") String page, @RequestParam(defaultValue = "5") int size) {
        int p = Integer.parseInt(page);
        var paginatedUserMessages = messageService.getPaginatedUserMessages(p, size, userId);
        long messagesCount = messageService.getUserMessagesCount(userId);

        model.addAttribute("page", p);
        model.addAttribute("messagesCount", messagesCount);
        model.addAttribute("userId", userId);
        model.addAttribute("userMessages", paginatedUserMessages);
        model.addAttribute("nextpage", paginatedUserMessages.getLast().getId());

        return "messages/usermessages";
    }



    @GetMapping("usernextpage")
    public String loadMoreUserMessages(@RequestParam Long userId, Model model,
                                       @RequestParam(defaultValue = "1") String page,
                                       @AuthenticationPrincipal OAuth2User oauth2User,
                                       @RequestParam(defaultValue = "5") int size) {


        int p = Integer.parseInt(page);
        var paginatedUserMessages = messageService.getPaginatedUserMessages(p, size, userId);
        long messagesCount = messageService.getUserMessagesCount(userId);


        Integer githubId;
        Optional<User> loggedInUser = Optional.empty();

        if (oauth2User != null) {
            githubId = oauth2User.getAttribute("id");
            loggedInUser = userService.findByUserId(githubId);
        }

        model.addAttribute("page", p);
        model.addAttribute("messagesCount", messagesCount);
        model.addAttribute("userId", userId);
        model.addAttribute("userMessages", paginatedUserMessages);
        model.addAttribute("nextpage", paginatedUserMessages.getLast().getId());
        loggedInUser.ifPresent(user -> model.addAttribute("loggedInUser", user));
        return "messages/usernextpage";
    }
}