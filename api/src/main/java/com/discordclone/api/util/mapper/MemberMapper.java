package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.MemberDto;
import com.discordclone.api.model.Member;
import com.discordclone.api.repository.ProfileRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class MemberMapper {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public MemberMapper(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getRole(),
                member.getProfile().getId(),
                member.getProfile().getId(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                profileMapper.toProfileDTO(member.getProfile())
        );
    }

    public Member fromDto(MemberDto memberDto) {
        return new Member()
                .setProfile(profileRepository.findById(memberDto.profileId()).orElseThrow())
                .setRole(memberDto.role())
                .setCreatedAt(memberDto.createdAt())
                .setUpdatedAt(memberDto.updatedAt())
                .setServers(new HashSet<>());
    }
}
