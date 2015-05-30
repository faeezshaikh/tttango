package com.faeez.service;

import java.util.List;

import com.faeez.model.MeetRequester;

public interface LikeMeService {

	public void addLikeMe(Long liked_by,Long liked_who);
	public List<MeetRequester> getProfilesWhoLikedMe(Long profile_id);
	public List<MeetRequester> getProfilesWhoILiked(Long profile_id);
}
