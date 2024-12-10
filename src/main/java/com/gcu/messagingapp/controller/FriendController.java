package com.gcu.messagingapp.controller;

import com.gcu.messagingapp.model.User;
import com.gcu.messagingapp.service.FriendService;
import com.gcu.messagingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller handling all friend-related operations.
 * Manages friend requests, friend lists, and friend-related views.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
@Controller
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    /**
     * Shows the friends page with friend lists and requests
     * 
     * @param user Currently authenticated user
     * @param model Spring MVC model for view data
     * @return View name for friends page
     */
    @GetMapping
    public String showFriendsPage(@AuthenticationPrincipal User user, Model model) {
        // Add current friends list
        model.addAttribute("friends", friendService.getFriendsList(user.getId()));

        // Add pending friend requests
        model.addAttribute("pendingRequests",
                friendService.getPendingRequestsForUser(user.getId()));

        // Add sent friend requests
        model.addAttribute("sentRequests",
                friendService.getSentRequestsForUser(user.getId()));

        // Add available users to add as friends
        model.addAttribute("availableUsers",
                userService.getAvailableUsers(user.getId()));

        return "friends";
    }

    /**
     * Handles sending a friend request
     */
    @PostMapping("/request")
    public String sendFriendRequest(@AuthenticationPrincipal User user,
                                    @RequestParam String receiverId,
                                    RedirectAttributes redirectAttributes) {
        try {
            friendService.sendFriendRequest(user.getId(), receiverId);
            redirectAttributes.addFlashAttribute("success",
                    "Friend request sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/friends";
    }

    /**
     * Handles accepting or rejecting a friend request
     */
    @PostMapping("/request/{requestId}")
    public String handleFriendRequest(@PathVariable String requestId,
                                      @RequestParam boolean accept,
                                      RedirectAttributes redirectAttributes) {
        try {
            friendService.handleFriendRequest(requestId, accept);
            redirectAttributes.addFlashAttribute("success",
                    accept ? "Friend request accepted!" : "Friend request rejected!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/friends";
    }
}