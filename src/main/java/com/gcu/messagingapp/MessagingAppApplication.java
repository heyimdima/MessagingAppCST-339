package com.gcu.messagingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for the MessagingApp
 * This class serves as the entry point for the messaging application
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
@SpringBootApplication
public class MessagingAppApplication {
    /**
     * Main method that starts the Spring Boot application
     * 
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(MessagingAppApplication.class, args);
    }
}
