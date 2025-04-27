package com.discordclone.api.config;

import com.discordclone.api.model.SocketIOConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketsConfig {
    @Bean
    public SocketIOConfig socketIOConfig() {
        SocketIOConfig config = new SocketIOConfig();
        config.configure();
        return config;
    }
}
