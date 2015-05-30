package com.faeez.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.faeez.dao.MeetMeDao;


// Phase 1 - done. All methods present are functional and used.
// for some reason service not working for this class only.
//@Service
@Transactional
public class MeetMeServiceImpl implements MeetMeService {

	@Autowired
//	@Qualifier("meetMeDao")
	private MeetMeDao meetMeDao;
	

	public void setMeetMeDao(MeetMeDao meetMeDao) {
		this.meetMeDao = meetMeDao;
	}

	@Override
	public List<com.faeez.model.MeetRequester> getProfilesWhoWannaMeetMe(Long profile_id) {
		return  meetMeDao.getProfilesWhoWannaMeetMe(profile_id);
	}
	
	
	@Override
	public List<com.faeez.model.MeetRequester> getProfilesWhoIWannaMeet(Long profile_id) {
		return meetMeDao.getProfilesWhoIWannaMeet(profile_id);
	}
}
