package com.faeez.service;

import java.util.List;

import com.faeez.model.MeetRequester;

public interface ProfileViewService {

	public List<MeetRequester>  getProfilesWhoViewedMe(Long profile_id);
	public List<MeetRequester> getProfilesWhoIViewed(Long profile_id);
}
