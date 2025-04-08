package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.MemberDto;
import com.discordclone.api.model.Member;
import com.discordclone.api.repository.ProfileRepository;

import java.util.HashSet;

public final class MemberMapper {
    private static ProfileRepository profileRepository;

    public MemberMapper(ProfileRepository profileRepository) {
        MemberMapper.profileRepository = profileRepository;
    }

    public static MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getRole(),
                member.getProfile().getId(),
                member.getProfile().getId(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                ProfileMapper.toProfileDTO(member.getProfile())
        );
    }

    public static Member fromDto(MemberDto memberDto) {
        return new Member()
                .setProfile(profileRepository.findById(memberDto.profileId()).orElseThrow())
                .setRole(memberDto.role())
                .setCreatedAt(memberDto.createdAt())
                .setUpdatedAt(memberDto.updatedAt())
                .setServers(new HashSet<>());
    }
}
