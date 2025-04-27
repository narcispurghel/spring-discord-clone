package com.discordclone.api.repository;

import com.discordclone.api.entity.Profile;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, UUID> {
    Optional<Profile> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Profile> getProfileById(UUID id);
}
