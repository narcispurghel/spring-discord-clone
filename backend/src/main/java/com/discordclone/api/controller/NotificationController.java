package com.discordclone.api.controller;

import com.discordclone.api.entity.Message;
import com.discordclone.api.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/messages/channel-message")
public class NotificationController {
    private final MessageRepository messageRepository;

    public NotificationController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @PostMapping()
    public Message sendMessage(
            @RequestParam(value = "channelId") UUID channelId,
            @RequestParam(value = "serverId") UUID serverId,
            @RequestBody Message message) {
        return messageRepository.save(message);
    }

}
