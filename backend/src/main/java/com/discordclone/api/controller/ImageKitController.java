package com.discordclone.api.controller;

import com.discordclone.api.config.ImageKitConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ImageKitController {

    private final ImageKitConfiguration imageKitConfiguration;

    public ImageKitController(ImageKitConfiguration imageKitConfiguration) {
        this.imageKitConfiguration = imageKitConfiguration;
    }

    @GetMapping("/api/upload/client-side-uploading")
    public Map<String, String> getAuthParams() {
        return imageKitConfiguration.getAuthParams();
    }
}