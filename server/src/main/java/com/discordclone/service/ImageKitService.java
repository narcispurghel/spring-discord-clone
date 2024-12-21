package com.discordclone.service;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class ImageKitService {

    private final ImageKit imageKit;

    public ImageKitService() {
        imageKit = ImageKit.getInstance();
        imageKit.setConfig(new Configuration(
                "public_nHTOgK+Y8J7IW7J7HQ4SYCHOL8U=",
                "private_vIde96Y2YEch/Hy7ET2+mWZGHYg=",
                "https://ik.imagekit.io/vq8udofpo"
        ));
    }

    public Map<String, String> getAuthParams() {
        String expire = getExpire();

        Map<String, String> authParams = imageKit.getAuthenticationParameters();
        authParams.put("expire", expire);

        return authParams;
    }

    private String getExpire() {
        // Get current Unix timestamp and add 1 hour
        long currentUnixTimestamp = Instant.now().getEpochSecond();
        long expireTimestamp = currentUnixTimestamp + 1000;

        return String.valueOf(expireTimestamp);
    }
}
