package com.discordclone.api.service;

import com.discordclone.api.entity.Member;

import java.util.List;
import java.util.UUID;

public interface MemberService {
    Member createMember(Member member);
    List<Member> getMembersByProfileId(UUID profileId);
}
