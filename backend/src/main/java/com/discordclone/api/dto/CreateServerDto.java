package com.discordclone.api.dto;

public class CreateServerDto {
    private String serverName;

    private String serverImage;

    public String getServerImage() {
        return serverImage;
    }

    public CreateServerDto setServerImage(String serverImage) {
        this.serverImage = serverImage;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public CreateServerDto setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

}
