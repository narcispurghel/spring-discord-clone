package com.discordclone.api.util;

import com.discordclone.api.entity.Channel;
import com.discordclone.api.entity.Member;
import com.discordclone.api.entity.Profile;
import com.discordclone.api.entity.Server;
import com.discordclone.api.model.domain.*;

import java.util.List;
import java.util.UUID;

public class ModelConverter {
    private ModelConverter() {
    }

    public static ProfileDTO convertToProfileDTO(Profile profile) {
        if (profile == null) {
            return null;
        }

        List<MemberDTO> members = profile.getMembers() == null ? null :  profile.getMembers().stream()
                .map(ModelConverter::convertToMemberDTO)
                .toList();

        return new ProfileDTO(
                profile.getId(),
                profile.getName(),
                profile.getEmail(),
                members,
                profile.getImageUrl(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }

    public static MemberDTO convertToMemberDTO(Member member) {
        if (member == null) {
            return null;
        }

        UUID serverId = member.getServer() == null ? null : member.getServer().getId();

        return new MemberDTO(
                member.getId(),
                member.getRole(),
                serverId,
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    public static ServerWithMembersAndChannelsDTO convertToServerWithMembersAndChannelsDTO(Server server) {
        if (server == null) {
            return null;
        }

        List<MemberDTO> members = server.getMembers().stream()
                .map(ModelConverter::convertToMemberDTO)
                .toList();

        List<ChannelDTO> channels = server.getChannels().stream()
                .map(ModelConverter::convertToChannelDTO)
                .toList();

        return new ServerWithMembersAndChannelsDTO(
                server.getId(),
                server.getName(),
                server.getImageUrl(),
                server.getInviteCode(),
                server.getCreatedAt(),
                server.getUpdatedAt(),
                server.getMembers().stream()
                        .filter(member -> member.getRole() == Role.OWNER)
                        .toList()
                        .getFirst()
                        .getId(),
                members,
                channels);
    }

    public static ChannelDTO convertToChannelDTO(Channel channel) {
        if (channel == null) {
            return null;
        }

        UUID serverId = channel.getServer() == null ? null : channel.getServer().getId();

        return new ChannelDTO(
            channel.getId(),
                channel.getChannelName(),
                channel.getType(),
                serverId,
                channel.getCreatedAt(),
                channel.getUpdatedAt()
        );
    }

    public static ServerDTO convertToServerDTO(Server server) {
        if (server == null) {
            return null;
        }

        UUID ownerId = server.getMembers() == null ? null : server.getMembers().stream()
                .filter(member -> member.getRole() == Role.OWNER)
                .toList()
                .getFirst()
                .getId();

        return new ServerDTO(
                server.getId(),
                server.getName(),
                server.getImageUrl(),
                server.getInviteCode(),
                ownerId,
                server.getCreatedAt(),
                server.getUpdatedAt()
        );
    }
}
