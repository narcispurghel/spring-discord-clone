package com.discordclone.api.util.mapper;

import com.discordclone.api.model.MemberDto;
import com.discordclone.api.entity.Member;

import java.util.UUID;

public final class MemberMapper {

    public static MemberDto toDto(Member member, UUID profileId, UUID serverId) {
        if (member == null) return null;

        return new MemberDto(
                member.getId(),
                member.getRole(),
                profileId,
                serverId,
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    public static Member fromDto(MemberDto memberDto) {
        if (memberDto == null) return null;

        return new Member()
                .setRole(memberDto.role())
                .setCreatedAt(memberDto.createdAt())
                .setUpdatedAt(memberDto.updatedAt());
    }
}
