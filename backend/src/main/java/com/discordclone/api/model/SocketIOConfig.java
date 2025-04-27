package com.discordclone.api.model;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketIOConfig {
    private final Logger logger = LoggerFactory.getLogger(SocketIOConfig.class);
    private SocketIOServer server;

    public void configure() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8081);
        config.getAuthorizationListener();
        server = new SocketIOServer(config);
        server.addConnectListener(onConnect());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("chat:channel:messages", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender) {
                server.getRoomOperations(data).sendEvent("chat:channel:messages",
                        "Welcome to channel: " + data);
            }
        });
        server.start();
    }

    private ConnectListener onConnect() {
        return (client) -> {
            logger.info("New connection");
            String url = client.getHandshakeData().getSingleUrlParam("sid");
            logger.info("client: {}", client);
            logger.info("url from client: {}", url);
        };
    }

    private DisconnectListener onDisconnected() {
        return _ -> logger.info("Disconnected");
    }

    private DataListener<SocketIOMessage> onChatMessage() {
        return (client, data, _) -> {
            logger.info("Received message {} from {}", data, client.getSessionId());
            client.sendEvent("/", data);
        };
    }

    public void stop() {
        if (server != null) {
            server.stop();
            logger.info("SocketIO server stopped");
        }
    }

}
