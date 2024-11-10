package com.gcu.messagingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller handling the root URL and basic navigation
 */
@Controller
public class HomeController {

    /**
     * Redirects root URL to messages page
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/messages";
    }

    /**
     * Shows the login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}