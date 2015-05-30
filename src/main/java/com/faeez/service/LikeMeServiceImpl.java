package com.faeez.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faeez.dao.LikeMeDao;
import com.faeez.model.LikeMe;
import com.faeez.model.MeetRequester;

@Service
@Transactional
public class LikeMeServiceImpl implements LikeMeService {

	private LikeMeDao likeMeDao;
	
	public void setLikeMeDao(LikeMeDao likeMeDao) {
		this.likeMeDao = likeMeDao;
	}

	@Override
	public void addLikeMe(Long liked_by, Long liked_who) {
		LikeMe likeMe = new LikeMe();
		likeMe.setLiked_by(liked_by);
		likeMe.setLiked_who(liked_who);
		likeMeDao.addLikeMe(likeMe);
	}

	@Override
	public List<MeetRequester> getProfilesWhoLikedMe(Long profile_id) {
		return likeMeDao.getProfilesWhoLikedMe(profile_id);
	}


	@Override
	public List<MeetRequester> getProfilesWhoILiked(Long profile_id) {
		return likeMeDao.getProfilesWhoILiked(profile_id); 
	}

}
