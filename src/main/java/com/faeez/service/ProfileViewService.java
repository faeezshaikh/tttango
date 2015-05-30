package com.faeez.service;

import java.util.List;

import com.faeez.model.ProfileView;
import com.faeez.ui.model.ProfileViewer;

public interface ProfileViewService {

	public List<ProfileViewer>  getProfilesWhoViewedMe(Long profile_id);
	public List<ProfileView> getProfilesWhoIViewed(Long profile_id);
}
