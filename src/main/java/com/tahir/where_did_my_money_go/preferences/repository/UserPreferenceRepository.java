package com.tahir.where_did_my_money_go.preferences.repository;

import com.tahir.where_did_my_money_go.preferences.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {

    Optional<UserPreference> findByUserId(UUID userId);
}