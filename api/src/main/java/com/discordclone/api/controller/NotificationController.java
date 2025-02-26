package com.discordclone.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/notification")
    public String sendMessage(String message) {
        System.out.println("message" + message);
        return message;
    }
}
