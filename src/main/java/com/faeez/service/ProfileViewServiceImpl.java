package com.faeez.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faeez.dao.ProfileViewDao;
import com.faeez.model.MeetRequester;

@Service
@Transactional
public class ProfileViewServiceImpl implements ProfileViewService {

	private ProfileViewDao profileViewDao;

	public void setProfileViewDao(ProfileViewDao profileViewDao) {
		this.profileViewDao = profileViewDao;
	}

	@Override
	public List<MeetRequester> getProfilesWhoViewedMe(Long profile_id) {
		return profileViewDao.getProfilesWhoViewedMe(profile_id);
	}

	@Override
	public List<MeetRequester> getProfilesWhoIViewed(Long profile_id) {
		return profileViewDao.getProfilesWhoIViewed(profile_id);
	}

}
