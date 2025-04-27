package com.discordclone.api.config;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;

import java.util.Map;

@org.springframework.context.annotation.Configuration
public class ImageKitConfiguration {
    private final ImageKit imageKit = ImageKit.getInstance();

    final String PUBLIC_KEY = "public_nHTOgK+Y8J7IW7J7HQ4SYCHOL8U=";
    final String PRIVATE_KEY = "private_vIde96Y2YEch/Hy7ET2+mWZGHYg=";
    final String URL_ENDPOINT = "https://ik.imagekit.io/vq8udofpo";

    public ImageKitConfiguration() {
        configureImageKit();
    }

    private void configureImageKit() {
        Configuration config = new Configuration(PUBLIC_KEY, PRIVATE_KEY, URL_ENDPOINT);
        imageKit.setConfig(config);
    }

    public Map<String, String> getAuthParams() {
        Long expire = getExpire();
        return imageKit.getAuthenticationParameters(null, expire);
    }

    private Long getExpire() {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000;
        return currentUnixTimestamp + 3599;
    }
}
