package com.faeez.service;

import java.util.List;

import com.faeez.ui.model.Liker;

public interface LikeMeService {

	public void addLikeMe(Long liked_by,Long liked_who);
	public List<Liker> getProfilesWhoLikedMe(Long profile_id);
	public List<Liker> getProfilesWhoILiked(Long profile_id);
}
