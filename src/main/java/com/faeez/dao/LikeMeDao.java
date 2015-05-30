package com.faeez.dao;

import java.util.List;

import com.faeez.model.LikeMe;
import com.faeez.model.MeetRequester;

public interface LikeMeDao {

	
	public List<MeetRequester> getProfilesWhoLikedMe(Long profile_id);
	public List<MeetRequester> getProfilesWhoILiked(Long profile_id);
	public void addLikeMe(LikeMe likeMe);
	public boolean addLikeMeRecord(Long like_by, Long like_who);
	public boolean removeLikeRecord(Long unlike_by, Long unlike_who);
	public boolean getLikeStatus(Long logged_in_user, Long profile_in_question);

}
