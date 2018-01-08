package com.dao;

import com.model.ProfilePicture;

public interface ProfilePictureDao {
	void saveOrUpdateProfilePicture(ProfilePicture profilePicture);
	ProfilePicture getProfilePicture(String username);
}
