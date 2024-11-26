package com.ox.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ox.banking.dto.UserProfileDTO;
import com.ox.banking.entity.UserProfile;
import com.ox.banking.repository.UserProfileRepository;

@Service
public class UserService {
	@Autowired
	private UserProfileRepository userProfileRepository;

	public UserProfileDTO getUserProfile(String userId) {
		return userProfileRepository.findByUserIdAndValidToIsNull(userId).map(this::convertToDTO)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}

	
	private UserProfileDTO convertToDTO(UserProfile profile) {
		UserProfileDTO dto = new UserProfileDTO();
		dto.setUserId(profile.getUserId());
		dto.setProfileType(profile.getProfileType());
		dto.setLanguage(profile.getLanguage());
		dto.setContractType(profile.getContractType());
		dto.setActive(profile.isActive());
		dto.setProducts(profile.getProducts());
		dto.setLastLogin(profile.getLastLogin());
		dto.setPreferredName(profile.getPreferredName());
		dto.setTextVersion(profile.getTextVersion());
		return dto;
	}

	private void updateUserProfileFromDTO(UserProfile profile, UserProfileDTO dto) {
		profile.setProfileType(dto.getProfileType());
		profile.setLanguage(dto.getLanguage());
		profile.setContractType(dto.getContractType());
		profile.setActive(dto.isActive());
		profile.setProducts(dto.getProducts());
		profile.setPreferredName(dto.getPreferredName());
		profile.setTextVersion(dto.getTextVersion());
	}
}