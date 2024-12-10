package com.gcu.messagingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller handling basic navigation and home page routing.
 * Manages root URL and login page access.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
@Controller
public class HomeController {

    /**
     * Handles root URL request
     * Redirects to messages page
     * 
     * @return Redirect string to messages page
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/messages";
    }

    /**
     * Displays the login page
     * 
     * @return View name for login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}