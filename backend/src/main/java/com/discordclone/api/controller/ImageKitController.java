package com.discordclone.api.controller;

import com.discordclone.api.config.ImageKitConfiguration;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
