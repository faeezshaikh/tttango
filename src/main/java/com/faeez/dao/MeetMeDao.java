package com.faeez.dao;

import java.util.List;

import com.faeez.model.MeetMe;
import com.faeez.model.MeetRequester;

public interface MeetMeDao {

	
	public List<MeetRequester> getProfilesWhoWannaMeetMe(Long profile_id);
	public List<MeetRequester> getProfilesWhoIWannaMeet(Long profile_id);
	public boolean addMeetMeRecord(Long meet_request_by, Long meet_who);
	void addMeetMe(MeetMe e);
	

}
