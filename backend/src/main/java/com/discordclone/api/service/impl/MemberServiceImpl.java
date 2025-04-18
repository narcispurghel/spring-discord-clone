package com.discordclone.api.service.impl;

import com.discordclone.api.entity.Member;
import com.discordclone.api.repository.MemberRepository;
import com.discordclone.api.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMember(Member member) {
        memberRepository.save(member);
    }
}
