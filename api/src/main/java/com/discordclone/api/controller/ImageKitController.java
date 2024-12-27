package com.discordclone.api.controller;

import com.discordclone.api.service.ImageKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ImageKitController {

    @Autowired
    private ImageKitService imageKitService;

    @GetMapping("/api/upload/client-side-uploading")
    public Map<String, String> getAuthParams() {
        return imageKitService.getAuthParams();
    }
}