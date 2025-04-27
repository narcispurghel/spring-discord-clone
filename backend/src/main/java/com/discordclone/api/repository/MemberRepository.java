package com.discordclone.api.repository;

import com.discordclone.api.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<List<Member>> getMemberByProfile_Id(UUID profileId);

    Member findByProfileId(UUID profileId);
}
