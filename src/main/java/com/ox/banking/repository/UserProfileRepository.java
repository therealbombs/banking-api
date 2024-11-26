package com.ox.banking.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ox.banking.entity.UserProfile;
import com.ox.banking.repository.base.BaseHistoricalRepository;

@Repository
public interface UserProfileRepository extends BaseHistoricalRepository<UserProfile, String> {
	Optional<UserProfile> findByUserIdAndValidToIsNull(String userId);
}