package com.faeez.dao;

import java.util.List;

import com.faeez.model.ProfileView;

public interface ProfileViewDao {

	public void addProfileView(ProfileView e);
	public List<ProfileView> getProfilesWhoViewedMe(Long profile_id);
	public List<ProfileView> getProfilesWhoIViewed(Long profile_id);
//	public ProfileView getExistingProfileView(Long viewed_by,Long viewed_who);
	public void updateProfileView(ProfileView p);
	public boolean addProfileView(Long viewed_by, Long viewed_who);
}
