package com.discordclone.controller;

import com.discordclone.service.ImageKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ImageKitController {

    @Autowired
    private ImageKitService imageKitService;

    @GetMapping("/api/upload/client-side-uploading")
    public Map<String, String> getAuthParams() {
        return imageKitService.getAuthParams();
    }
}
