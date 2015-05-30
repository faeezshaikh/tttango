package com.faeez.service;

import java.util.List;

//Phase 1 - done. All methods present are functional and used.
public interface MeetMeService {

	public List<com.faeez.model.MeetRequester> getProfilesWhoWannaMeetMe(Long profile_id);
	public List<com.faeez.model.MeetRequester> getProfilesWhoIWannaMeet(Long profile_id);

	
	
}
