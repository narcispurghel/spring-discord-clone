package com.discordclone.api.service.impl;

import com.discordclone.api.entity.Member;
import com.discordclone.api.exception.impl.UnprocessableEntityException;
import com.discordclone.api.repository.MemberRepository;
import com.discordclone.api.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Member createMember(Member member) {
        if (member == null) {
            throw new UnprocessableEntityException(
                    "Invalid member",
                    "member must be a non-null value",
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "member"
            );
        }

        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public List<Member> getMembersByProfileId(UUID profileId) {
        return memberRepository.getMemberByProfile_Id(profileId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
