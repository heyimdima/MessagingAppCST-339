package com.gcu.messagingapp.controller;

import com.gcu.messagingapp.model.Message;
import com.gcu.messagingapp.model.User;
import com.gcu.messagingapp.service.MessageService;
import com.gcu.messagingapp.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller handling all message-related HTTP requests.
 * Manages message views, message sending, and message retrieval operations.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private FriendService friendService;

    /**
     * Displays the messages page with chat interface
     * 
     * @param currentUser The authenticated user
     * @param friendId Optional ID of friend to show chat with
     * @param model Spring MVC model
     * @return View name for messages page
     */
    @GetMapping("/messages")
    public String showMessages(@AuthenticationPrincipal User currentUser,
                               @RequestParam(required = false) String friendId,
                               Model model) {
        // Add current user to model
        model.addAttribute("currentUser", currentUser);

        // Get and add friends list
        model.addAttribute("friends", friendService.getFriendsList(currentUser.getId()));

        // If a friend is selected, get their details and messages
        if (friendId != null) {
            // Get messages between current user and selected friend
            model.addAttribute("messages",
                    messageService.getMessagesBetweenUsers(currentUser.getId(), friendId));

            // Get selected friend's details
            User selectedFriend = friendService.getFriendById(friendId);
            if (selectedFriend != null) {
                model.addAttribute("selectedFriend", selectedFriend);
            }
        }

        return "messages";
    }

    /**
     * Handles sending a new message between users
     * @param senderId ID of the sending user
     * @param receiverId ID of the receiving user
     * @param content Content of the message
     * @param redirectAttributes For flash messages
     * @return Redirect to messages view
     */
    @PostMapping("/messages/send")
    public String sendMessage(@AuthenticationPrincipal User currentUser,
                              @RequestParam String receiverId,
                              @RequestParam String content,
                              RedirectAttributes redirectAttributes) {
        try {
            Message message = new Message();
            message.setSenderId(currentUser.getId());
            message.setReceiverId(receiverId);
            message.setContent(content);

            messageService.sendMessage(message);
            return "redirect:/messages?friendId=" + receiverId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/messages";
        }
    }
}