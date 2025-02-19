package com.discordclone.api.repository;

import com.discordclone.api.entity.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, UUID> {
    Optional<Profile> findByEmail(String email);
}
